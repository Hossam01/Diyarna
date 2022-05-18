package com.example.diyarna.domain.usecase

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.diyarna.R

typealias LoadPhoto = LoadPhotoUseCase

class LoadPhotoUseCase {


    operator fun invoke(imageView: ImageView, url: String, context: Context) =
        Glide.with(imageView).load(url)
            .placeholder(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.logo
                )
            ).into(imageView)

}