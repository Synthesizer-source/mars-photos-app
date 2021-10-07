package com.synthesizer.source.mars.data.repository

import com.synthesizer.source.mars.data.api.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RoverRepository @Inject constructor(private val service: ApiService) {

    suspend fun fetchPhotos(roverName: String, page: Int) = flow {
        val data = service.getPhotos("curiosity", 1).body()!!
        emit(data)
    }
}