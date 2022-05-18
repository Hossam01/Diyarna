package com.example.diyarna.presentation.main.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.databinding.MoreFragmentBinding
import com.example.diyarna.databinding.ProductFragmentBinding
import com.example.diyarna.presentation.main.MainActivity

class MoreFragment : BaseFragment<MoreFragmentBinding>(MoreFragmentBinding::inflate){


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle("More")
    }
}