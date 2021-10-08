package com.synthesizer.source.mars.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.synthesizer.source.mars.data.Resource
import com.synthesizer.source.mars.data.repository.RoverRepository
import com.synthesizer.source.mars.domain.model.PhotoListItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okio.IOException
import retrofit2.HttpException

class PhotoListPagingSource @AssistedInject constructor(
    @Assisted("roverName") private val roverName: String,
    @Assisted("camera") private val camera: String?,
    private val repository: RoverRepository
) : PagingSource<Int, PhotoListItem>() {

    private var sol = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoListItem> {
        val pageIndex = params.key ?: 1
        return try {
            val resource = repository.fetchPhotoList(
                roverName = roverName,
                sol = sol,
                camera = camera,
                page = pageIndex
            )
            if (resource is Resource.Success) {
                val response = resource.data
                if (response.isNullOrEmpty()) {
                    sol++
                    return LoadResult.Page(data = emptyList(), prevKey = 0, nextKey = 1)
                }

                val prevKey = if (pageIndex <= 1) null else pageIndex - 1
                return LoadResult.Page(
                    data = response,
                    prevKey = prevKey,
                    nextKey = pageIndex + 1
                )
            } else {
                return LoadResult.Error(Throwable((resource as Resource.Failure).message))
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override val keyReuseSupported: Boolean
        get() = true

    override fun getRefreshKey(state: PagingState<Int, PhotoListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}