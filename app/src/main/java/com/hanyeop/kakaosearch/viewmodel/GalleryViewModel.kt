package com.hanyeop.kakaosearch.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.hanyeop.kakaosearch.repository.KakaoRepository

class GalleryViewModel @ViewModelInject constructor(
    private val repository: KakaoRepository
) : ViewModel()