package com.synthesizer.source.mars.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.synthesizer.source.mars.data.api.ApiService
import com.synthesizer.source.mars.data.remote.PhotoResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okio.IOException
import retrofit2.HttpException

class PhotoListPagingSource @AssistedInject constructor(
    @Assisted private val roverName: String,
    private val service: ApiService
) :
    PagingSource<Int, PhotoResponse>() {

    private var sol = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoResponse> {
        val pageIndex = params.key ?: 1
        return try {
            val response = service.getPhotos(roverName, sol, pageIndex).body()!!
            if (response.photos.isNullOrEmpty()) {
                sol++
                return LoadResult.Page(data = emptyList(), prevKey = 0, nextKey = 1)
            }

            val prevKey = if (pageIndex == 1) null else pageIndex - 1
            LoadResult.Page(data = response.photos, prevKey = prevKey, nextKey = pageIndex + 1)

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