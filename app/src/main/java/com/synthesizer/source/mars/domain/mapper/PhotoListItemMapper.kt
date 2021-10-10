package com.synthesizer.source.mars.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.synthesizer.source.mars.data.remote.PhotoListItemResponse
import com.synthesizer.source.mars.domain.model.PhotoListItem

fun PhotoListItemResponse.toDomain() = PhotoListItem(id = id, imgSrc = imgSrc)

fun PagingData<PhotoListItemResponse>.toDomain() = map {
    it.toDomain()
}