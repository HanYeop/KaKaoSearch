package com.hanyeop.kakaosearch.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hanyeop.kakaosearch.R
import com.hanyeop.kakaosearch.databinding.FragmentDetailBinding
import java.text.SimpleDateFormat

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args by navArgs<DetailFragmentArgs>()

    // 참조 관리
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailBinding.bind(view)

        binding.apply {
            val image = args.document

            Log.d("test5", "${image.image_url}")

            Glide.with(this@DetailFragment)
                .load(image.image_url)
                .error(R.drawable.empty_icon)
                .listener(object : RequestListener<Drawable> {

                    // 로드 실패 했을 때
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar2.isVisible = false
                        return false
                    }

                    // 로딩 완료됐을 때
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar2.isVisible = false
                        collectText.isVisible = true
                        timeText.isVisible = true
                        siteText.isVisible = true
                        return false
                    }
                })
                .into(DetailImageView)

            collectText.text = image.collection
            siteText.text = image.siteName
            val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val time = mFormat.format(image.datetime)
            timeText.text = time
        }
    }

    // 프래그먼트는 뷰보다 오래 지속 . 프래그먼트의 onDestroyView() 메서드에서 결합 클래스 인스턴스 참조를 정리
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}