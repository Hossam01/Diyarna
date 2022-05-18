package com.example.diyarna.presentation.main.Dialog

import com.example.diyarna.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.example.diyarna.databinding.DonateDialogBinding





class DonateDialog : DialogFragment() {

    lateinit var binding: DonateDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        binding= DonateDialogBinding.inflate(layoutInflater)
        binding.donate.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.MyAlertDialogTheme
    }

}