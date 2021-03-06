package com.synthesizer.source.mars.data.remote


import com.google.gson.annotations.SerializedName

data class RoverDetailResponse(
    @SerializedName("cameras")
    val cameras: List<RoverCameraResponse>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("landing_date")
    val landingDate: String,
    @SerializedName("launch_date")
    val launchDate: String,
    @SerializedName("max_date")
    val maxDate: String,
    @SerializedName("max_sol")
    val maxSol: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("total_photos")
    val totalPhotos: Int
)