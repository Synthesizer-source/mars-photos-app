package com.synthesizer.source.mars.ui.photodetail

import androidx.lifecycle.*
import com.synthesizer.source.mars.data.remote.PhotoDetailInfoResponse
import com.synthesizer.source.mars.data.repository.RoverRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class PhotoDetailViewModel @AssistedInject constructor(
    @Assisted private val id: Int,
    private val repository: RoverRepository
) : ViewModel() {

    private val _photoDetail = MutableLiveData<PhotoDetailInfoResponse>()
    val photoDetail: LiveData<PhotoDetailInfoResponse> = _photoDetail

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
            _photoDetail.value = it.photo
        }
    }
}