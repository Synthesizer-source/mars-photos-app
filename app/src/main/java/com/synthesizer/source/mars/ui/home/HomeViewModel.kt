package com.synthesizer.source.mars.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.synthesizer.source.mars.R
import com.synthesizer.source.mars.data.repository.RoverRepository
import com.synthesizer.source.mars.data.source.PhotoListPagingSource
import com.synthesizer.source.mars.domain.model.PhotoListItem
import com.synthesizer.source.mars.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RoverRepository) : BaseViewModel() {

    private var _photoList = MutableLiveData<PagingData<PhotoListItem>>()
    val photoList: LiveData<PagingData<PhotoListItem>> = _photoList

    init {
        fetchPhotoList(getRoverName(0)!!)
    }

    fun fetchPhotoList(roverName: String, camera: String? = null) = viewModelScope.launch {
        Pager(PagingConfig(pageSize = 25)) {
            PhotoListPagingSource(
                roverName = roverName,
                repository = repository,
                camera = camera
            )
        }.flow.cachedIn(viewModelScope)
            .map {
                if (loading.value?.peekContent() == true) {
                    it.map { item ->
                        onFirstItemLoaded()
                        item
                    }
                } else it
            }
            .onStart { onLoading() }
            .catch { onFailure(it.message) }
            .collect {
                onSuccess(it)
            }
    }

    private fun onFirstItemLoaded() {
        if (loading.value?.peekContent() == true) {
            onDataLoaded()
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