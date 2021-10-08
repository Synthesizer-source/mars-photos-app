package com.synthesizer.source.mars.ui.photodetail

import androidx.lifecycle.*
import com.synthesizer.source.mars.data.Resource
import com.synthesizer.source.mars.data.repository.RoverRepository
import com.synthesizer.source.mars.domain.model.PhotoDetail
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class PhotoDetailViewModel @AssistedInject constructor(
    @Assisted private val id: Int,
    private val repository: RoverRepository
) : ViewModel() {

    private val _photoDetail = MutableLiveData<PhotoDetail>()
    val photoDetail: LiveData<PhotoDetail> = _photoDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(id: Int): PhotoDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            id: Int
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return assistedFactory.create(id) as T
                }
            }
    }

    init {
        fetchPhotoDetail()
    }

    private fun fetchPhotoDetail() = viewModelScope.launch {
        repository.fetchPhotoDetail(id).collect {
            when (it) {
                is Resource.Failure -> onFailure()
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it.data)
            }
        }
    }

    private fun onSuccess(data: PhotoDetail) {
        _photoDetail.value = data
        _isLoading.value = false
    }

    private fun onLoading() {
        _isLoading.value = true
    }

    private fun onFailure() {}
}