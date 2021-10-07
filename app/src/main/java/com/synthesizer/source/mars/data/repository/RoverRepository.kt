package com.synthesizer.source.mars.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.synthesizer.source.mars.data.api.ApiService
import com.synthesizer.source.mars.data.source.PhotoListPagingSource
import javax.inject.Inject

class RoverRepository @Inject constructor(private val service: ApiService) {

    fun fetchPhotoList(roverName: String, page: Int) = Pager(PagingConfig(pageSize = 25)) {
        PhotoListPagingSource(service)
    }.flow
}