package com.synthesizer.source.mars.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.synthesizer.source.mars.data.api.ApiService
import com.synthesizer.source.mars.data.remote.PhotoListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val service: ApiService) : ViewModel() {

    private var _photoList = MutableLiveData<PhotoListResponse>()
    val photoList: LiveData<PhotoListResponse> = _photoList

    init {
        loadData()
    }

    private fun loadData() {
        service.getPhotos("curiosity", 1).enqueue(object : Callback<PhotoListResponse> {
            override fun onResponse(
                call: Call<PhotoListResponse>,
                response: Response<PhotoListResponse>
            ) {
                if (response.isSuccessful) {
                    _photoList.value = response.body()
                }
            }

            override fun onFailure(call: Call<PhotoListResponse>, t: Throwable) {
            }
        })
    }
}