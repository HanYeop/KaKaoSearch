package com.hanyeop.kakaosearch.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hanyeop.kakaosearch.api.KakaoApi
import com.hanyeop.kakaosearch.model.Document
import com.hanyeop.kakaosearch.util.Constants.Companion.EMPTY_RESULT
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class KakaoPagingSource(
    private val kakaoApi: KakaoApi,
    private val query : String,
    private val sort : String
) : PagingSource<Int,Document>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Document> {

        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = kakaoApi.searchImage(
                query = query,
                sort = sort,
                page = position,
                size = params.loadSize
            )
            val images = response.body()?.documents

            LoadResult.Page(
                data = images ?: listOf(
                    Document(
                        EMPTY_RESULT,
                        "",
                        "",
                        "",
                        null
                    )
                ),
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (response.body()?.metaData?.isEnd == true) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Document>): Int? {
        TODO("Not yet implemented")
    }
}