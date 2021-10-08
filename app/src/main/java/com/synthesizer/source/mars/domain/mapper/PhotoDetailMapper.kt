package com.synthesizer.source.mars.domain.mapper

import com.synthesizer.source.mars.data.remote.PhotoDetailResponse
import com.synthesizer.source.mars.domain.model.PhotoDetail
import com.synthesizer.source.mars.util.capitalize
import com.synthesizer.source.mars.util.getShortAndFullName

fun PhotoDetailResponse.toDomain() =
    PhotoDetail(
        id = photo.id,
        date = photo.earthDate,
        roverName = photo.rover.name,
        cameraName = photo.camera.getShortAndFullName(),
        status = photo.rover.status.capitalize(),
        launchDate = photo.rover.launchDate,
        landingDate = photo.rover.landingDate,
        imgSrc = photo.imgSrc
    )