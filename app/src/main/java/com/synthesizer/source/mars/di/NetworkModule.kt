package com.synthesizer.source.mars.di

import com.google.gson.GsonBuilder
import com.synthesizer.source.mars.data.api.ApiInterceptor
import com.synthesizer.source.mars.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/"

    @Singleton
    private val gson = GsonBuilder().create()

    @Singleton
    private val interceptor = ApiInterceptor()

    @Singleton
    private val client = OkHttpClient().newBuilder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(interceptor).build()

    @Singleton
    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Singleton
    @Provides
    fun provideService(): ApiService = retrofit.create(ApiService::class.java)
}