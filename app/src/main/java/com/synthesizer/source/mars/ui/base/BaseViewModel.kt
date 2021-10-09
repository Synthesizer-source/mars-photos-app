package com.synthesizer.source.mars.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    open fun onFailure(message: String?) {
        message?.let { _errorMessage.value = it }
    }

    open fun onLoading() {
        _loading.value = true
    }

    open fun onDataLoaded() {
        _loading.value = false
    }
}