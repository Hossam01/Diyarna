package com.example.diyarna.presentation.main.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.diyarna.R
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.data.remote.model.ProductItem
import com.example.diyarna.databinding.ProductFragmentBinding
import com.example.diyarna.presentation.main.MainActivity
import com.example.diyarna.presentation.main.home.HomeViewModel
import com.example.diyarna.presentation.main.products.adapter.ProductAdapter
import com.example.diyarna.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment:BaseFragment(), ProductAdapter.ItemAdapterListener {
    lateinit var binding: ProductFragmentBinding
    val homeViewModel : HomeViewModel by viewModels()
    lateinit var adapteritem: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= ProductFragmentBinding.inflate(layoutInflater)
        (requireActivity() as MainActivity).setToolBarTitle("Product")
        binding.container.setOnRefreshListener {
            homeViewModel.getDataItem()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments!=null) {
            var itemDto = requireArguments().getParcelableArrayList<ProductItem>("itemProduct")
            assignAdapter()
            itemDto?.let { adapteritem.submitList(it) }
        }else{
            getProductData()
        }
    }

    private fun getProductData() {
        lifecycleScope.launch {
            homeViewModel.getData().collect {
                when (it.status) {
                    Status.OK -> {
                        assignAdapter()
                        adapteritem.submitList(it.results!!.product)
                        binding.container.isRefreshing=false
                    }
                    Status.ERROR -> {
                        if (it.message != null) {
                            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
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

    override fun onItemClicked(item: ProductItem) {
        (requireActivity() as MainActivity).addProductToCard(item)
    }

    override fun onDetailsItemClicked(item: ProductItem) {
        val bundle = Bundle()
        bundle.putParcelable("itemDetails",item)
        (requireActivity() as MainActivity).navToDestination(R.id.nav_details,bundle)
    }

    override fun onItemRemoved(item: ProductItem) {
        (requireActivity() as MainActivity).removeProductFromCard(item)

    }

    override fun updateItem(item: ProductItem) {
        (requireActivity() as MainActivity).updateProductinCard(item)
    }
}