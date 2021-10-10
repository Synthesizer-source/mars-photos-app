package com.synthesizer.source.mars.data

import retrofit2.Response

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    class Success<T>(val data: T) : Resource<T>()
    class Failure<T>(val message: String?, val data: T? = null) : Resource<T>()

    companion object {

        inline fun <T> of(f: () -> Response<T>): Resource<T> {
            return try {
                val response = f()
                if (response.code() in 200..299) {
                    val data = response.body()!!
                    Success(data)
                } else {
                    Failure(response.message())
                }
            } catch (throwable: Throwable) {
                Failure(throwable.message)
            }
        }
    }
}