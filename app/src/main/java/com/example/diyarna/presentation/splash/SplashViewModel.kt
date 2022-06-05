package com.example.diyarna.presentation.splash

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyarna.data.repository.DataRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.log

class SplashViewModel @ViewModelInject constructor(val dataRepoImpl: DataRepoImpl):ViewModel() {

    private val userActive = MutableStateFlow<String?>(null)
    private val language = MutableStateFlow<String?>(null)

    fun getUserActive()=userActive
    fun getlanguage(): MutableStateFlow<String?> {
        return language
    }


    init {
        getUser()
        getLanguage()
    }
    private fun getUser()=viewModelScope.launch {
        userActive.emit(dataRepoImpl.get("name"))
    }

    fun getLanguage()=viewModelScope.launch {

        language.emit(dataRepoImpl.get("language"))
    }

}