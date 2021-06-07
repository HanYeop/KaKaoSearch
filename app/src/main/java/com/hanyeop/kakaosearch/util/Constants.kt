package com.hanyeop.kakaosearch.util

import com.hanyeop.kakaosearch.util.ApiKey.Companion.REST_API_KEY

// 상수 저장
class Constants {

    companion object{
        const val BASE_URL = "https://dapi.kakao.com"

        // 개인 API 사용
        const val AUTH_HEADER = "KakaoAK $REST_API_KEY"

        // 결과값 없을 때
        const val EMPTY_RESULT = "EMPTY_RESULT"
    }
}