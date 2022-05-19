package com.example.diyarna.presentation.main.more

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyarna.data.repository.DataRepoImpl
import com.example.diyarna.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MoreViewModel @ViewModelInject constructor(val dataRepoImpl: DataRepoImpl): ViewModel() {

    var logOutmutableStateFlow= MutableStateFlow<Resource<Boolean>>(Resource.loading(false))
    var logInmutableStateFlow= MutableStateFlow<Resource<Boolean>>(Resource.loading(false))

    fun getAction()=logOutmutableStateFlow
    fun getLoginorLogout()=logInmutableStateFlow

    init {
        getUser()
    }

    private fun getUser()=viewModelScope.launch {
        Log.d("TAG", "getUser: ${dataRepoImpl.get("name")}")
        if (dataRepoImpl.get("name")!=null) {
            logInmutableStateFlow.emit(Resource.success(true))
        }
        else
            logInmutableStateFlow.emit(Resource.error("Something Happened",false))

    }

    fun logout()
    {
        viewModelScope.launch {
            if (dataRepoImpl.remove("name")) {
                logOutmutableStateFlow.emit(Resource.success(true))
            }
            else
            {
                logOutmutableStateFlow.emit(Resource.error("Something Happened",false))
            }
        }
    }
}