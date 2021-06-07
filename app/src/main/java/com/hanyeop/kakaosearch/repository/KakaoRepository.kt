package com.hanyeop.kakaosearch.repository

import com.hanyeop.kakaosearch.api.KakaoApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoRepository @Inject constructor(
    private val kakaoApi: KakaoApi
){
//    suspend fun searchImage(query : String, sort : String) : Response<ImageSearchResponse> {
//        return RetrofitInstance.api.searchImage(query = query, sort = sort, page = 1, size = 5)
//    }
}