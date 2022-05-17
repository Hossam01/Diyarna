package com.example.diyarna.presentation.main.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.databinding.MoreFragmentBinding
import com.example.diyarna.presentation.main.MainActivity

class MoreFragment : BaseFragment(){
    lateinit var binding: MoreFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= MoreFragmentBinding.inflate(layoutInflater)
        (requireActivity() as MainActivity).setToolBarTitle("More")


        return binding.root
    }
}