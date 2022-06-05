package com.example.diyarna.presentation.main.Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.diyarna.R
import com.example.diyarna.databinding.LanguageDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import android.widget.RadioGroup
import com.example.diyarna.util.Constants
import android.content.Intent
import android.content.res.Configuration

import com.example.diyarna.presentation.main.MainActivity
import android.os.Build

import android.util.DisplayMetrics
import com.example.diyarna.domain.usecase.ChangeLanguage
import java.util.*


@AndroidEntryPoint
class LanguageDialog: DialogFragment() {

    lateinit var binding: LanguageDialogBinding
    val languageViewModel : LanguageViewModel by viewModels()
    lateinit var mLang: String

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        binding= LanguageDialogBinding.inflate(layoutInflater)

        binding.closeBack.setOnClickListener {
            dismiss()
        }

       binding.langRG.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.arabicRBtn ->
                    mLang = Constants.ARABIC_LANGUAGE
                R.id.englishRBtn ->
                    mLang = Constants.ENGLISH_LANGUAGE
            }
        })
        binding.save.setOnClickListener {
            languageViewModel.SaveLanguage(mLang)
            var changeLanguage=ChangeLanguage()
            context?.let { it1 -> changeLanguage(mLang, it1) }
            restartApp()
            dismiss()
        }

        binding
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.MyAlertDialogTheme
    }

    fun restartApp() {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        activity?.finish()
    }

    private fun changeLocal(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        resources.updateConfiguration(configuration,resources.displayMetrics)
    }


}