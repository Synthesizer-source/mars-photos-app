package com.synthesizer.source.mars.data.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY&page=1")
    fun getPhotos(): Call<HashMap<String, Any>>
}