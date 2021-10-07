package com.synthesizer.source.mars.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synthesizer.source.mars.data.remote.PhotoListResponse
import com.synthesizer.source.mars.data.repository.RoverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RoverRepository) : ViewModel() {

    private var _photoList = MutableLiveData<PhotoListResponse>()
    val photoList: LiveData<PhotoListResponse> = _photoList

    init {
        fetchPhotos()
    }

    private fun fetchPhotos() = viewModelScope.launch {
        repository.fetchPhotos("curiosity", 1).collect {
            _photoList.value = it
        }
    }
}