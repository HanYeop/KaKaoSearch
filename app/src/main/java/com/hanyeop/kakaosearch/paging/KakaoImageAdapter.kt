package com.hanyeop.kakaosearch.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hanyeop.kakaosearch.R
import com.hanyeop.kakaosearch.databinding.ItemImageBinding
import com.hanyeop.kakaosearch.model.Document

class KakaoImageAdapter(private val listener : OnItemClickListener)
    : PagingDataAdapter<Document, KakaoImageAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class ImageViewHolder(private val binding : ItemImageBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            // 아이템 클릭 시
            binding.root.setOnClickListener {
                // 그 포지션에 맞는 아이템을 넘겨줌
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item != null){
                        listener.onItemClick(item)
                    }
                }
            }
        }

        // 이미지와 텍스트를 바인딩
        fun bind(image : Document){
            binding.apply{
                Glide.with(itemView)
                    .load(image.thumbnail_url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade()) // 애니메이션
                    .error(R.drawable.empty_icon)
                    .into(imageView)
                sitenameText.text = image.sitename
            }
        }
    }

    // 아이템 포지션 넘겨주기 위한 인터페이스
    interface OnItemClickListener {
        fun onItemClick(document : Document)
    }


    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<Document>() {
            override fun areItemsTheSame(oldItem: Document, newItem: Document) =
                oldItem.image_url == newItem.image_url

            override fun areContentsTheSame(oldItem: Document, newItem: Document) =
                oldItem == newItem
        }
    }
}

