package com.synthesizer.source.mars.data.api

import com.synthesizer.source.mars.data.remote.PhotoListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("rovers/{rover_name}/photos?sol=1000&api_key=DEMO_KEY")
    suspend fun getPhotos(
        @Path("rover_name") roverName: String,
        @Query("page") page: Int
    ): Response<PhotoListResponse>
}