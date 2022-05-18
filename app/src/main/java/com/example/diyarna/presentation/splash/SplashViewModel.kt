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

    fun getUserActive()=userActive

    init {
        getUser()
    }
    private fun getUser()=viewModelScope.launch {
        userActive.emit(dataRepoImpl.get("name"))
        Log.d("TAG", "getUser: ${dataRepoImpl.get("name")}")
    }
}