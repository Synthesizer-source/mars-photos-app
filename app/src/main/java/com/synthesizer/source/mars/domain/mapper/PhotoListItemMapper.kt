package com.synthesizer.source.mars.domain.mapper

import com.synthesizer.source.mars.data.remote.PhotoListItemResponse
import com.synthesizer.source.mars.domain.model.PhotoListItem

fun PhotoListItemResponse.toDomain() = PhotoListItem(id = id, imgSrc = imgSrc)