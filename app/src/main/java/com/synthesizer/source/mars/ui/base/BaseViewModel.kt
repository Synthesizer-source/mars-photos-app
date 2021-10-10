package com.synthesizer.source.mars.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.synthesizer.source.mars.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    private val _loading = MutableLiveData<Event<Boolean>>()
    val loading: LiveData<Event<Boolean>> = _loading

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    open fun onFailure(message: String?) {
        message?.let { _errorMessage.value = Event(it) }
    }

    open fun onLoading() {
        _loading.value = Event(true)
    }

    open fun onDataLoaded() {
        _loading.value = Event(false)
    }
}