package com.nightfury.moviedb.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    private val isHomeReselected = MutableLiveData<Boolean>()

    fun setHomeReselected(isReselected: Boolean) {
        this.isHomeReselected.value = isReselected
    }

    fun isHomeReselected(): LiveData<Boolean> {
        return isHomeReselected
    }
}