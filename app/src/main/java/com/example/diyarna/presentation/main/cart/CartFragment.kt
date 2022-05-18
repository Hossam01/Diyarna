package com.example.diyarna.presentation.main.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.data.remote.model.ProductItem
import com.example.diyarna.databinding.CartFragmentBinding
import com.example.diyarna.databinding.HomeFragmentBinding

import com.example.diyarna.presentation.main.MainActivity
import com.example.diyarna.presentation.main.cart.adapter.CartAdapter

class CartFragment : BaseFragment<CartFragmentBinding>(CartFragmentBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle("Cart")

        var itemDto= requireArguments().getParcelableArrayList<ProductItem>("itemCart")
        var cartAdapter= CartAdapter()
        itemDto?.let { cartAdapter.submitList(it) }
        binding.rvItemlist.adapter=cartAdapter
        binding.totalPrice.text=""+itemDto!!.sumOf { it.totalPrice }+" EGP"
    }

}