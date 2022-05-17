package com.example.diyarna.presentation.main


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.diyarna.R
import com.example.diyarna.base.BaseActivity
import com.example.diyarna.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.GravityCompat
import com.example.diyarna.data.remote.model.ProductItem


@AndroidEntryPoint

class MainActivity : BaseActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var navController: NavController
    private val cardListProducts: ArrayList<ProductItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)


        binding.toolbar.searchToolbarIV.setOnClickListener {
            navToDestination(R.id.nav_donate_view)
        }
        binding.toolbar.cartToolbarIV.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("itemCart",cardListProducts)
            navToDestination(R.id.nav_cart,bundle)

        }
        Log.d("TAG", "onCreate: "+intent.getStringExtra("name"))

    }

        fun setToolBarTitle(title: String) {
            binding.toolbar.titleToolbarIv.text = title
        }

    fun navToDestination(action: NavDirections?) {
        if (navController != null && action != null) navController.navigate(action)
    }

    fun navToDestination(resId: Int) {
        if (navController != null && resId != 0) navController.navigate(resId)
    }

    fun navToDestination(resId: Int, bundleArgs: Bundle) {
        if (navController != null && resId != 0) navController.navigate(resId, bundleArgs)
    }

    fun addProductToCard(product: ProductItem) {
            this.cardListProducts.add(product)
            Log.d("TAG", "addProductToCard: " + cardListProducts)
            binding.toolbar.cartCountIV.visibility = View.VISIBLE
            binding.toolbar.cartCountIV.text = cardListProducts.size.toString() + ""
            cardListProducts.find { it.id == product.id }!!.totalPrice = product.count*product.price

    }

    fun removeProductFromCard(product: ProductItem) {
        cardListProducts.remove(product)
        if (cardListProducts.size == 0) {
            binding.toolbar.cartCountIV.visibility=View.INVISIBLE
        } else {
            binding.toolbar.cartCountIV.text=cardListProducts.size.toString() + ""
        }
    }
    
    fun updateProductinCard(product: ProductItem){
        if (product.count==0)
        {
            removeProductFromCard(product)
        }
        else {
            cardListProducts.find { it.id == product.id }!!.count = product.count
            cardListProducts.find { it.id == product.id }!!.totalPrice = product.count*product.price
        }
        Log.d("TAG", "addProductToCard: " + cardListProducts)
    }



}