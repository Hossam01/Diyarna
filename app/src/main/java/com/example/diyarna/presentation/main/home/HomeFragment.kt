package com.example.diyarna.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.databinding.HomeFragmentBinding

class HomeFragment : BaseFragment() {

    lateinit var binding:HomeFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= HomeFragmentBinding.inflate(layoutInflater)

        return binding.root
    }
}