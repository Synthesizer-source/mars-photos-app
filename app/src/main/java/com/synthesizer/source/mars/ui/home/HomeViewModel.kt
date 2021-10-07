package com.synthesizer.source.mars.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synthesizer.source.mars.data.api.ApiService
import com.synthesizer.source.mars.data.remote.PhotoListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val service: ApiService) : ViewModel() {

    private var _photoList = MutableLiveData<PhotoListResponse>()
    val photoList: LiveData<PhotoListResponse> = _photoList

    init {
        fetchPhotos()
    }

    private fun fetchPhotos() = viewModelScope.launch {
        try {
            val data = service.getPhotos("curiosity", 1).body()!!
            _photoList.value = data
        } catch (exception: Exception) {
        }
    }
}