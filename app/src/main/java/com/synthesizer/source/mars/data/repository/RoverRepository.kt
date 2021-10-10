package com.synthesizer.source.mars.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.synthesizer.source.mars.data.Resource
import com.synthesizer.source.mars.data.api.ApiService
import com.synthesizer.source.mars.data.source.PhotoListPagingSource
import com.synthesizer.source.mars.domain.mapper.toDomain
import com.synthesizer.source.mars.util.map
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RoverRepository @Inject constructor(private val service: ApiService) {

    fun fetchPhotoList(roverName: String, camera: String?) =
        Pager(PagingConfig(pageSize = 25)) {
            PhotoListPagingSource(
                roverName = roverName,
                service = service,
                camera = camera
            )
        }.flow.map { it.toDomain() }

    fun fetchPhotoDetail(id: Int) = flow {
        emit(Resource.of { service.getPhotoDetail(id).map { it.toDomain() } })
    }.onStart { emit(Resource.Loading()) }
        .catch { emit(Resource.Failure(it.message)) }
}