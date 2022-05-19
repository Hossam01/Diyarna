package com.example.diyarna.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.diyarna.R
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.data.remote.model.CampaignsItem
import com.example.diyarna.data.remote.model.ProductItem
import com.example.diyarna.databinding.HomeFragmentBinding
import com.example.diyarna.databinding.MoreFragmentBinding
import com.example.diyarna.presentation.main.MainActivity
import com.example.diyarna.presentation.main.home.adapter.CampaginsAdapter
import com.example.diyarna.presentation.main.home.adapter.ProductAdapter
import com.example.diyarna.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) , ProductAdapter.ItemAdapterListener,CampaginsAdapter.ItemAdapterListener {

    val homeViewModel :HomeViewModel by viewModels()
    lateinit var adapteritem: ProductAdapter
    lateinit var adapterCampaginsItem: CampaginsAdapter
    lateinit var productList:ArrayList<ProductItem>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle(resources.getString(R.string.title_home))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProductItem()
        getCampaignsItem()

        binding.checkallCampaign.setOnClickListener {
            (requireActivity() as MainActivity).navToDestination(R.id.nav_campaign)
        }

        binding.checkallProduct.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("itemProduct",productList)
            (requireActivity() as MainActivity).navToDestination(R.id.nav_product,bundle)
        }

    }

    fun getProductItem() {
        lifecycleScope.launch {
            homeViewModel.getData().collect {
                when (it.status) {
                    Status.OK -> {
                        productList= it.results!!.product as ArrayList<ProductItem>
                        assignAdapter()
                        adapteritem.submitList(it.results!!.product.subList(0,2))
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


    private fun assignAdapter() {
        adapteritem = ProductAdapter()
        adapteritem.mListener=this
        binding.rvItemlist.adapter = adapteritem
    }

    private fun assignCampaginsAdapter() {
        adapterCampaginsItem = CampaginsAdapter()
        adapterCampaginsItem.mListener=this
        binding.rvCampaignlist.adapter = adapterCampaginsItem
    }

    override fun onItemClicked(item: ProductItem) {
        (requireActivity() as MainActivity).addProductToCard(item)
    }

    override fun onItemRemoved(item: ProductItem) {
        (requireActivity() as MainActivity).removeProductFromCard(item)
    }

    override fun updateItem(item: ProductItem) {
        (requireActivity() as MainActivity).updateProductinCard(item)

    }

    override fun goToDetails(item: ProductItem) {
        val bundle = Bundle()
        bundle.putParcelable("itemDetails",item)
        (requireActivity() as MainActivity).navToDestination(R.id.nav_details,bundle)
    }

    override fun onItemClicked(item: CampaignsItem) {
        (requireActivity() as MainActivity).navToDestination(R.id.nav_donate_view)
    }

}