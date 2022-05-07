package com.example.diyarna.presentation.main.campaign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.databinding.CampaignFragmentBinding

class CampaignFragment:BaseFragment() {

    lateinit var binding: CampaignFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= CampaignFragmentBinding.inflate(layoutInflater)

        return binding.root
    }
}