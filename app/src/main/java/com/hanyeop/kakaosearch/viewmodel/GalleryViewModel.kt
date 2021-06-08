package com.hanyeop.kakaosearch.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hanyeop.kakaosearch.repository.KakaoRepository

class GalleryViewModel @ViewModelInject constructor(
    private val repository: KakaoRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    // 라이브 데이터 변경 시 다른 라이브 데이터 발행
    val images = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    // 라이브 데이터 변경
    fun searchImages(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "페이커"
    }
}