package com.example.diyarna.base

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

import com.google.android.material.appbar.AppBarLayout

import androidx.coordinatorlayout.widget.CoordinatorLayout

import androidx.annotation.NonNull
import com.example.diyarna.data.Utils
import com.example.diyarna.presentation.main.MainActivity
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger


open class BaseActivity : AppCompatActivity() {
    lateinit var snackbar: Snackbar


    open fun showSnackBar(
        view: View,
        msg: String
    ) {
        if (view is CoordinatorLayout || view is AppBarLayout) {
            snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
        } else {
            Utils.showToast(this, msg)
        }
    }
}