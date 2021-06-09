package com.hanyeop.kakaosearch.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.hanyeop.kakaosearch.R
import com.hanyeop.kakaosearch.databinding.FragmentGalleryBinding
import com.hanyeop.kakaosearch.paging.KakaoImageAdapter
import com.hanyeop.kakaosearch.ui.adapter.ImageLoadStateAdapter
import com.hanyeop.kakaosearch.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    // 뷰모델 생성
    private val viewModel by viewModels<GalleryViewModel>()

    // 참조 관리
    private var _binding : FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰바인딩
        _binding = FragmentGalleryBinding.bind(view)

        val adapter = KakaoImageAdapter()

        // 로딩상태 리스너
        adapter.addLoadStateListener { combinedLoadStates ->
            binding.apply {
                // 로딩 중 일 때
                progressBar.isVisible = combinedLoadStates.source.refresh is LoadState.Loading

                // 로딩 중이지 않을 때
                galleryRecyclerView.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading

                // 로딩 에러 발생 시
                retryButton.isVisible = combinedLoadStates.source.refresh is LoadState.Error
                errorText.isVisible = combinedLoadStates.source.refresh is LoadState.Error

                // 로딩 되지 않음 & 로드할 수 없음 & 개수 1 미만 (empty)
                if(combinedLoadStates.source.refresh is LoadState.NotLoading
                    && combinedLoadStates.append.endOfPaginationReached
                    && adapter.itemCount < 1){
                    galleryRecyclerView.isVisible = false
                    emptyText.isVisible = true
                    emptyImage.isVisible = true
                }
                else{
                    emptyImage.isVisible = false
                    emptyText.isVisible = false
                }

            }
        }

        binding.apply {
            galleryRecyclerView.setHasFixedSize(true) // 사이즈 고정
            // header, footer 설정
            galleryRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ImageLoadStateAdapter{ adapter.retry() },
                footer = ImageLoadStateAdapter{ adapter.retry() }
            )

            // 다시 시도하기 버튼
            retryButton.setOnClickListener {
                adapter.retry()
            }
        }

        // 관찰하여 변경 시 알림
        viewModel.images.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }

        // 메뉴 설정
        setHasOptionsMenu(true)
    }

    // 검색 창
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery,menu)

        // 아이템 연결
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            // 제출 버튼 눌렀을 때
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    binding.galleryRecyclerView.scrollToPosition(0) // 처음으로 돌아감
                    viewModel.searchImages(query)
                    searchView.clearFocus() // 포커스 없애기 (커서 없애기)
                }
                return true
            }

            // 검색어 값 변경시
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    // 프래그먼트는 뷰보다 오래 지속 . 프래그먼트의 onDestroyView() 메서드에서 결합 클래스 인스턴스 참조를 정리
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}