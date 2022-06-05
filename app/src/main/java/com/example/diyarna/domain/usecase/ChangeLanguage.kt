package com.example.diyarna.domain.usecase

import android.content.Context
import android.content.res.Configuration
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.diyarna.R
import java.util.*

class ChangeLanguage {

    operator fun invoke(language: String,context: Context) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        context.resources.updateConfiguration(configuration,context.resources.displayMetrics)
    }
}