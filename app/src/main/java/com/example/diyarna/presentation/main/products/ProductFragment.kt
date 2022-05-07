package com.example.diyarna.presentation.main.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.databinding.ProductFragmentBinding

class ProductFragment:BaseFragment() {
    lateinit var binding: ProductFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= ProductFragmentBinding.inflate(layoutInflater)

        return binding.root
    }
}