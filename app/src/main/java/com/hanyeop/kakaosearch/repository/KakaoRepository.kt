package com.hanyeop.kakaosearch.repository

import com.hanyeop.kakaosearch.api.RetrofitInstance
import com.hanyeop.kakaosearch.model.ImageSearchResponse
import retrofit2.Response

class KakaoRepository {

    suspend fun searchImage(query : String, sort : String) : Response<ImageSearchResponse> {
        return RetrofitInstance.api.searchImage(query = query, sort = sort, page = 1, size = 5)
    }
}