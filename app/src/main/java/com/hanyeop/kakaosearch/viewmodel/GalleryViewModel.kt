package com.hanyeop.kakaosearch.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.hanyeop.kakaosearch.repository.KakaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: KakaoRepository,
    state : SavedStateHandle
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