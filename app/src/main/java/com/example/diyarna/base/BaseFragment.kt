package com.example.diyarna.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.diyarna.domain.extention.castToActivity
import com.example.diyarna.domain.extention.showAppToast
import com.example.diyarna.presentation.main.MainActivity

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

open class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setToolbarTitle(title: String) {
        castToActivity<MainActivity> {
            it?.setToolBarTitle(title)
        }
    }

    fun showToast(message: String) {
        context?.showAppToast(message)
    }
}