package com.hanyeop.kakaosearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hanyeop.kakaosearch.databinding.ImageLoadStateFooterBinding

class ImageLoadStateAdapter(private val retry: () -> Unit)
    : LoadStateAdapter<ImageLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ImageLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding : ImageLoadStateFooterBinding)
        : RecyclerView.ViewHolder(binding.root){

        init{
            // 버튼 클릭 시 Fragment 에서 받아온 동작 호출 (retry)
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState : LoadState){
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState !is LoadState.Loading
                errorText.isVisible = loadState !is LoadState.Loading
            }
        }
    }


}