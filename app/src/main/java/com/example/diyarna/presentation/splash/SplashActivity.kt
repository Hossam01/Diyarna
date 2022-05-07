package com.example.diyarna.presentation.splash

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.example.diyarna.base.BaseActivity
import com.example.diyarna.databinding.ActivitySplashBinding
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.diyarna.presentation.login.LoginActivity
import com.example.diyarna.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.security.MessageDigest
import java.util.*


@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    lateinit var binding: ActivitySplashBinding
    private val viewModel:SplashViewModel by viewModels()
    val activityScope = CoroutineScope(Dispatchers.Main)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope.launch {
            showProgressBar(true)
            delay(3000)
            viewModel.getUserActive().collect {
                if (it!=null)
                {
                    gotoLogin(MainActivity())
                }else
                {
                    gotoLogin(LoginActivity())
                }

            }

        }
    }


    fun gotoLogin(activity: BaseActivity) {
        var intent = Intent(this@SplashActivity, activity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onPause() {
        activityScope.cancel()
        showProgressBar(false)
        super.onPause()
    }

    private fun showProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.mDilatingDotsProgressBar.show()
        } else {
            binding.mDilatingDotsProgressBar.hideNow()
        }
    }




}