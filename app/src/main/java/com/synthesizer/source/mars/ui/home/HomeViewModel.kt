package com.synthesizer.source.mars.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synthesizer.source.mars.R
import com.synthesizer.source.mars.data.repository.RoverRepository
import com.synthesizer.source.mars.domain.model.PhotoListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RoverRepository) : ViewModel() {

    private var _photoList = MutableLiveData<PagingData<PhotoListItem>>()
    val photoList: LiveData<PagingData<PhotoListItem>> = _photoList

    init {
        fetchPhotoList(getRoverName(0)!!)
    }

    fun fetchPhotoList(roverName: String, camera: String? = null) = viewModelScope.launch {
        repository.fetchPhotoList(roverName = roverName, camera = camera).cachedIn(viewModelScope)
            .collect {
                _photoList.value = it
            }
    }

    fun getRoverName(tabPosition: Int) = when (tabPosition) {
        0 -> "curiosity"
        1 -> "opportunity"
        2 -> "spirit"
        else -> null
    }

    fun getRoverCameraTypes(roverName: String) = when (roverName) {
        "curiosity" -> R.menu.menu_curiosity
        "opportunity" -> R.menu.menu_opportunity
        "spirit" -> R.menu.menu_spirit
        else -> null
    }
}