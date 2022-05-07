package com.example.diyarna.presentation.main

import android.os.Bundle
import android.util.Log
import com.example.diyarna.base.BaseActivity
import com.example.diyarna.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.ui.NavigationUI

import com.example.diyarna.R


import androidx.navigation.NavController
import androidx.navigation.Navigation


@AndroidEntryPoint

class MainActivity : BaseActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController: NavController = Navigation.findNavController(this,R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        Log.d("TAG", "onCreate: "+intent.getStringExtra("name"))
    }
}