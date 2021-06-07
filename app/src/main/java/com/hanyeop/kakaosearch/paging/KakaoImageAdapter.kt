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

class KakaoImageAdapter : PagingDataAdapter<Document, KakaoImageAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {

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

    class ImageViewHolder(private val binding : ItemImageBinding) : RecyclerView.ViewHolder(binding.root){

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




    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<Document>() {
            override fun areItemsTheSame(oldItem: Document, newItem: Document) =
                oldItem.image_url == newItem.image_url

            override fun areContentsTheSame(oldItem: Document, newItem: Document) =
                oldItem == newItem
        }
    }
}