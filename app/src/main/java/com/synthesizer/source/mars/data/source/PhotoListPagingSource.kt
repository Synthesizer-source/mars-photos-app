package com.synthesizer.source.mars.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.synthesizer.source.mars.data.api.ApiService
import com.synthesizer.source.mars.data.remote.PhotoResponse
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class PhotoListPagingSource @Inject constructor(private val service: ApiService) :
    PagingSource<Int, PhotoResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoResponse> {
        val pageIndex = params.key ?: 1
        return try {
            val response = service.getPhotos("curiosity", pageIndex).body()!!

            val responseData = mutableListOf<PhotoResponse>()
            responseData.addAll(response.photos)

            val prevKey = if (pageIndex == 1) null else pageIndex - 1
            LoadResult.Page(data = responseData, prevKey = prevKey, nextKey = pageIndex + 1)

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}