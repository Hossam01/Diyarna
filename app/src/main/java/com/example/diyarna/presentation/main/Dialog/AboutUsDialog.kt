package com.example.diyarna.presentation.main.Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.example.diyarna.R
import com.example.diyarna.data.local.model.MoreModel
import com.example.diyarna.databinding.AboutDialogBinding

class AboutUsDialog : DialogFragment() {

    lateinit var binding: AboutDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        binding= AboutDialogBinding.inflate(layoutInflater)
        var itemDto= requireArguments().getParcelable<MoreModel>("Setting")
        binding.header.text=getString(itemDto!!.name)
        binding.details.text=getString(itemDto!!.details)
        binding.closeBack.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.MyAlertDialogTheme
    }
}