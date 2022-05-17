package com.example.diyarna.presentation.main.campaign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.data.remote.model.ProductItem
import com.example.diyarna.databinding.CampaignFragmentBinding
import com.example.diyarna.presentation.main.MainActivity
import com.example.diyarna.presentation.main.home.HomeViewModel
import com.example.diyarna.presentation.main.home.adapter.CampaginsAdapter
import com.example.diyarna.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CampaignFragment:BaseFragment() {

    lateinit var binding: CampaignFragmentBinding
    val homeViewModel : HomeViewModel by viewModels()
    lateinit var adapterCampaginsItem: CampaginsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= CampaignFragmentBinding.inflate(layoutInflater)
        (requireActivity() as MainActivity).setToolBarTitle("Campaign")
        binding.container.setOnRefreshListener {
            homeViewModel.getCampaignsItem()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments==null)
        {
        getCampaignsItem()
        }
    }

    fun getCampaignsItem() {
        lifecycleScope.launch {
            homeViewModel.getCampaignsData().collect {
                when (it.status) {
                    Status.OK -> {
                        assignCampaginsAdapter()
                        adapterCampaginsItem.submitList(it.results!!.campaigns)
                        binding.container.isRefreshing=false
                    }
                    Status.ERROR -> {
                        if (it.message != null) {
                            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG)
                                .show()
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