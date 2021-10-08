package com.synthesizer.source.mars.util

import com.synthesizer.source.mars.data.remote.CameraResponse

fun CameraResponse.getShortAndFullName() = "($name) $fullName"