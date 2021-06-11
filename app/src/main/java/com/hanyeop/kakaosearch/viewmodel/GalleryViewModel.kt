package com.hanyeop.kakaosearch.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.hanyeop.kakaosearch.repository.KakaoRepository

class GalleryViewModel @ViewModelInject constructor(
    private val repository: KakaoRepository,
    @Assisted state : SavedStateHandle
) : ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY,DEFAULT_QUERY)

    // 라이브 데이터 변경 시 다른 라이브 데이터 발행
    val images = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    // 라이브 데이터 변경
    fun searchImages(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "페이커"
    }
}