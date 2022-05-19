package com.example.diyarna.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoreModel(val name:Int, val resource:Int, val details: Int):Parcelable
