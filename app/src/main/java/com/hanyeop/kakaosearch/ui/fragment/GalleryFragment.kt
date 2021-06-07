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

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding : FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = KakaoImageAdapter()

        binding.apply {
            galleryRecyclerView.setHasFixedSize(true)
            galleryRecyclerView.adapter = adapter
        }

        viewModel.images.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}