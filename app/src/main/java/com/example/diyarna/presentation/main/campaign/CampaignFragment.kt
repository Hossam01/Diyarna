package com.example.diyarna.presentation.main.campaign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.diyarna.R
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.data.remote.model.ProductItem
import com.example.diyarna.databinding.CampaignFragmentBinding
import com.example.diyarna.databinding.HomeFragmentBinding
import com.example.diyarna.domain.extention.castToActivity
import com.example.diyarna.presentation.main.MainActivity
import com.example.diyarna.presentation.main.home.HomeViewModel
import com.example.diyarna.presentation.main.home.adapter.CampaginsAdapter
import com.example.diyarna.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CampaignFragment:BaseFragment<CampaignFragmentBinding>(CampaignFragmentBinding::inflate) {

    val homeViewModel : HomeViewModel by activityViewModels()
    lateinit var adapterCampaginsItem: CampaginsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(resources.getString(R.string.campaigns))
        binding.campcontainer.setOnRefreshListener {
            homeViewModel.getCampaignsItem()
            binding.campcontainer.isRefreshing=false
        }

        getCampaignsItem()

    }

    fun getCampaignsItem() {
        lifecycleScope.launch {
            homeViewModel.getCampaignsData().collect {
                when (it.status) {
                    Status.OK -> {
                        assignCampaginsAdapter()
                        adapterCampaginsItem.submitList(it.results!!.campaigns)
                    }
                    Status.ERROR -> {
                        if (it.message != null) {
                            showToast(it.message)
                        }
                    }
                    Status.LOADING -> {
                    }
                }
            }
        }
    }

    private fun assignCampaginsAdapter() {
        adapterCampaginsItem = CampaginsAdapter()
        binding.rvItemlist.adapter = adapterCampaginsItem
    }



}