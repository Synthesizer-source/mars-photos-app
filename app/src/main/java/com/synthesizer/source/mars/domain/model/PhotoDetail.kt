package com.synthesizer.source.mars.domain.model

data class PhotoDetail(
    val id: Int,
    val date: String,
    val roverName: String,
    val cameraName: String,
    val status: String,
    val launchDate: String,
    val landingDate: String,
    val imgSrc: String
)