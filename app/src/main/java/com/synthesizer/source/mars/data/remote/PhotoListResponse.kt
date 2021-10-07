package com.synthesizer.source.mars.data.remote


import com.google.gson.annotations.SerializedName

data class PhotoListResponse(
    @SerializedName("photos")
    val photos: List<PhotoResponse>
)