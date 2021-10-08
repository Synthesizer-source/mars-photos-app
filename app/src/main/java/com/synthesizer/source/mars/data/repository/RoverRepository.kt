package com.synthesizer.source.mars.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.synthesizer.source.mars.data.api.ApiService
import com.synthesizer.source.mars.data.source.PhotoListPagingSource
import com.synthesizer.source.mars.domain.mapper.toDomain
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoverRepository @Inject constructor(private val service: ApiService) {

    fun fetchPhotoList(roverName: String, camera: String?) = Pager(PagingConfig(pageSize = 25)) {
        PhotoListPagingSource(
            roverName = roverName,
            service = service,
            camera = camera
        )
    }.flow.map { pagingData ->
        pagingData.map {
            it.toDomain()
        }
    }

    fun fetchPhotoDetail(id: Int) = flow {
        emit(service.getPhotoDetail(id).body()!!.toDomain())
    }
}