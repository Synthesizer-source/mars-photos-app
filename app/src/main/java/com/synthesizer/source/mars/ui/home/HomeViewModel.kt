package com.synthesizer.source.mars.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synthesizer.source.mars.R
import com.synthesizer.source.mars.data.repository.RoverRepository
import com.synthesizer.source.mars.domain.model.PhotoListItem
import com.synthesizer.source.mars.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RoverRepository) : BaseViewModel() {

    private var _photoList = MutableLiveData<PagingData<PhotoListItem>>()
    val photoList: LiveData<PagingData<PhotoListItem>> = _photoList

    private var _selectedRover: String = ""
    private var _selectedCamera: String? = null

    init {
        setCurrentRoverName(0)
    }

    private fun fetchPhotoList(roverName: String, camera: String? = null) = viewModelScope.launch {
        repository.fetchPhotoList(roverName, camera).cachedIn(viewModelScope)
            .onStart { onLoading() }
            .catch { onFailure(it.message) }.collect {
                onSuccess(it)
            }
    }

    private fun onSuccess(data: PagingData<PhotoListItem>) {
        _photoList.value = data
    }

    fun handleLoadStatesError(loadStates: CombinedLoadStates) {
        when {
            loadStates.refresh is LoadState.Error -> {
                onFailure((loadStates.refresh as LoadState.Error).error.message)
            }
            loadStates.append is LoadState.Error -> {
                onFailure((loadStates.append as LoadState.Error).error.message)
            }
            loadStates.prepend is LoadState.Error -> {
                onFailure((loadStates.prepend as LoadState.Error).error.message)
            }
        }
    }

    fun setCurrentRoverName(tabPosition: Int?) {
        clearData()
        _selectedRover = getRoverName(tabPosition)
        fetchPhotoList(roverName = _selectedRover, _selectedCamera)
    }

    fun setCurrentCameraType(camera: String?) {
        clearData()
        _selectedCamera = camera
        fetchPhotoList(roverName = _selectedRover, camera = _selectedCamera)
    }

    private fun clearData() {
        _photoList.value = PagingData.empty()
    }

    private fun getRoverName(tabPosition: Int?) = when (tabPosition) {
        1 -> "opportunity"
        2 -> "spirit"
        else -> "curiosity"
    }

    fun getRoverCameraTypes(tabPosition: Int?) = when (tabPosition) {
        1 -> R.menu.menu_opportunity
        2 -> R.menu.menu_spirit
        else -> R.menu.menu_curiosity
    }
}