package com.synthesizer.source.mars.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synthesizer.source.mars.data.remote.PhotoResponse
import com.synthesizer.source.mars.data.repository.RoverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RoverRepository) : ViewModel() {

    private var _photoList = MutableLiveData<PagingData<PhotoResponse>>()
    val photoList: LiveData<PagingData<PhotoResponse>> = _photoList

    init {
        fetchPhotoList()
    }

    private fun fetchPhotoList() = viewModelScope.launch {
        repository.fetchPhotoList("curiosity", 1).cachedIn(viewModelScope).collect {
            _photoList.value = it
        }
    }
}