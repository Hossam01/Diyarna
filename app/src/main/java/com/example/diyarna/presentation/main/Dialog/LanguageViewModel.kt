package com.example.diyarna.presentation.main.Dialog

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.diyarna.data.repository.DataRepoImpl

class LanguageViewModel @ViewModelInject constructor(val dataRepoImpl: DataRepoImpl): ViewModel() {

    fun SaveLanguage(language:String){
        dataRepoImpl.save("language",language)
    }
}