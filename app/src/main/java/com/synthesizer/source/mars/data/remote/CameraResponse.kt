package com.synthesizer.source.mars.data.remote


import com.google.gson.annotations.SerializedName

data class CameraResponse(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("rover_id")
    val roverId: Int
) {
    fun getShortAndFullName() = "($name) $fullName"
}