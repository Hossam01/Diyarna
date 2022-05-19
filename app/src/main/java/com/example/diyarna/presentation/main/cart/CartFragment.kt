package com.example.diyarna.presentation.main.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diyarna.R
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.data.remote.model.ProductItem
import com.example.diyarna.databinding.CartFragmentBinding
import com.example.diyarna.databinding.HomeFragmentBinding

import com.example.diyarna.presentation.main.MainActivity
import com.example.diyarna.presentation.main.cart.adapter.CartAdapter

class CartFragment : BaseFragment<CartFragmentBinding>(CartFragmentBinding::inflate),CartAdapter.ItemAdapterListener {
    lateinit var itemDto: List<ProductItem>
    lateinit var cartAdapter: CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle(resources.getString(R.string.cart))

        itemDto = requireArguments().getParcelableArrayList<ProductItem>("itemCart")!!
        cartAdapter= CartAdapter()
        cartAdapter.mListener=this
        EmpatyCardView(cartAdapter)
        binding.rvItemlist.adapter=cartAdapter
        updatePrice(itemDto)
    }

    private fun EmpatyCardView(cartAdapter: CartAdapter) {
        if (itemDto.size > 0) {
            binding.emptyCart.root.visibility = View.GONE
            itemDto.let { cartAdapter.submitList(it) }
        } else
            binding.emptyCart.root.visibility = View.VISIBLE
    }


    override fun updateItem(item: ProductItem) {
        (requireActivity() as MainActivity).updateProductinCard(item)
        updatePrice(itemDto)
    }
    override fun onItemRemoved(item: ProductItem) {
        (requireActivity() as MainActivity).removeProductFromCard(item)
        EmpatyCardView(cartAdapter)
    }

    fun updatePrice(itemDto:List<ProductItem>)
    {
        binding.totalPrice.text=""+itemDto!!.sumOf { it.totalPrice }+" EGP"

    }


}