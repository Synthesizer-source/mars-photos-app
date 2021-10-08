package com.synthesizer.source.mars.data.remote


import com.google.gson.annotations.SerializedName

data class PhotoListItemResponse(
    @SerializedName("camera")
    val camera: CameraResponse,
    @SerializedName("earth_date")
    val earthDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("img_src")
    val imgSrc: String,
    @SerializedName("rover")
    val rover: RoverResponse,
    @SerializedName("sol")
    val sol: Int
)