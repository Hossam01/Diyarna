package com.example.diyarna.presentation.main.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyarna.data.remote.model.Campaigns
import com.example.diyarna.data.remote.model.ProductItem
import com.example.diyarna.data.remote.model.ProductModel
import com.example.diyarna.data.repository.DataRepoImpl
import com.example.diyarna.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(val dataRepoImpl: DataRepoImpl):ViewModel() {
    private val itemDataList = MutableStateFlow<Resource<ProductModel>>(Resource.loading(null))
    private val itemCampaignsList = MutableStateFlow<Resource<Campaigns>>(Resource.loading(null))

    init {
        getDataItem()
        getCampaignsItem()
    }


    fun getData()=itemDataList
    fun getCampaignsData()=itemCampaignsList

    fun getDataItem() = viewModelScope.launch {
            itemDataList.emit(Resource.loading(null))
            dataRepoImpl.getProduct().let {
                if (it.body()!=null)
                    if (it.isSuccessful) {
                            itemDataList.emit(Resource.success(it.body()))
                    } else {
                        itemDataList.emit(Resource.error(it.errorBody().toString(), null))
                    }
                else
                    itemDataList.emit(Resource.error(it.errorBody().toString(), null))


            }
        }

    fun getCampaignsItem() = viewModelScope.launch {
            itemDataList.emit(Resource.loading(null))
            dataRepoImpl.getCampaigns().let {
                if (it.body()!=null)
                    if (it.isSuccessful) {
                            itemCampaignsList.emit(Resource.success(it.body()))
                    } else {
                        itemCampaignsList.emit(Resource.error(it.errorBody().toString(), null))
                    }
                else
                    itemCampaignsList.emit(Resource.error(it.errorBody().toString(), null))


            }
        }
}