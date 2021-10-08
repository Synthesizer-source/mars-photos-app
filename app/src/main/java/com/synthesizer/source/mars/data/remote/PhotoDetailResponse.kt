package com.synthesizer.source.mars.data.remote


import com.google.gson.annotations.SerializedName

data class PhotoDetailResponse(
    @SerializedName("photo")
    val photo: PhotoDetailInfoResponse
)