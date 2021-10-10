package com.synthesizer.source.mars.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.synthesizer.source.mars.data.Resource
import com.synthesizer.source.mars.data.api.ApiService
import com.synthesizer.source.mars.data.remote.PhotoListItemResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.atomic.AtomicInteger

class PhotoListPagingSource @AssistedInject constructor(
    @Assisted("roverName") private val roverName: String,
    @Assisted("camera") private val camera: String?,
    private val service: ApiService
) : PagingSource<Int, PhotoListItemResponse>() {

    private var sol = AtomicInteger(1)
    private var pageIndex = AtomicInteger(1)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoListItemResponse> {
        if (params.key != null) pageIndex.set(params.key!!)
        else pageIndex.set(1)
        return try {
            val resource = Resource.of {
                service.getPhotos(
                    roverName = roverName,
                    sol = sol.get(),
                    camera = camera,
                    page = pageIndex.get()
                )
            }
            if (resource is Resource.Success) {
                val data = resource.data.photos
                if (data.isNullOrEmpty()) {
                    sol.incrementAndGet()
                    return LoadResult.Page(
                        data = emptyList(),
                        prevKey = 0,
                        nextKey = 1
                    )
                }
                val prevKey = if (pageIndex.get() == 1) null else pageIndex.get() - 1
                return LoadResult.Page(
                    data = data,
                    prevKey = prevKey,
                    nextKey = pageIndex.get() + 1
                )
            } else {
                val errorMessage = if (resource is Resource.Failure) resource.message else null
                return LoadResult.Error(Throwable(errorMessage))
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

    override fun getRefreshKey(state: PagingState<Int, PhotoListItemResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}