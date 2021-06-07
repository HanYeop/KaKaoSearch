package com.hanyeop.kakaosearch.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hanyeop.kakaosearch.R
import com.hanyeop.kakaosearch.databinding.FragmentGalleryBinding
import com.hanyeop.kakaosearch.paging.KakaoImageAdapter
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

        binding.apply {
            galleryRecyclerView.setHasFixedSize(true) // 사이즈 고정
            galleryRecyclerView.adapter = adapter
        }

        viewModel.images.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
    }

    // 프래그먼트는 뷰보다 오래 지속 . 프래그먼트의 onDestroyView() 메서드에서 결합 클래스 인스턴스 참조를 정리
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}