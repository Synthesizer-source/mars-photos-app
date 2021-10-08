package com.synthesizer.source.mars.data.repository

import com.synthesizer.source.mars.data.Resource
import com.synthesizer.source.mars.data.api.ApiService
import com.synthesizer.source.mars.domain.mapper.toDomain
import com.synthesizer.source.mars.util.map
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RoverRepository @Inject constructor(private val service: ApiService) {

    suspend fun fetchPhotoList(roverName: String, sol: Int, camera: String?, page: Int) =
        Resource.of {
            service.getPhotos(roverName, sol, camera, page)
                .map { res -> res.photos.map { it.toDomain() } }
        }

    fun fetchPhotoDetail(id: Int) = flow {
        emit(Resource.of { service.getPhotoDetail(id).map { it.toDomain() } })
    }.onStart { emit(Resource.Loading()) }
        .catch { emit(Resource.Failure(it.message)) }
}