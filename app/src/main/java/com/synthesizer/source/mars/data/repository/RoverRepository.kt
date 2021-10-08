package com.synthesizer.source.mars.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.synthesizer.source.mars.data.api.ApiService
import com.synthesizer.source.mars.data.source.PhotoListPagingSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RoverRepository @Inject constructor(private val service: ApiService) {

    fun fetchPhotoList(roverName: String, camera: String?) = Pager(PagingConfig(pageSize = 25)) {
        PhotoListPagingSource(
            roverName = roverName,
            service = service,
            camera = camera
        )
    }.flow

    fun fetchPhotoDetail(id: Int) = flow {
        emit(service.getPhotoDetail(id).body()!!)
    }
}