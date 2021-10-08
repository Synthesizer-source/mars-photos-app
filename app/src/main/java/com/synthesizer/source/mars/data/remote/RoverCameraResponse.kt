package com.synthesizer.source.mars.data.remote


import com.google.gson.annotations.SerializedName

data class RoverCameraResponse(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("name")
    val name: String
)