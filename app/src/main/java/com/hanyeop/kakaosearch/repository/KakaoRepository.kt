package com.hanyeop.kakaosearch.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.hanyeop.kakaosearch.api.KakaoApi
import com.hanyeop.kakaosearch.paging.KakaoPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoRepository @Inject constructor(
    private val kakaoApi: KakaoApi
){
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { KakaoPagingSource(kakaoApi,query,"accuracy") }
        ).liveData

//    suspend fun searchImage(query : String, sort : String) : Response<ImageSearchResponse> {
//        return RetrofitInstance.api.searchImage(query = query, sort = sort, page = 1, size = 5)
//    }
}