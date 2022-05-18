package com.example.diyarna.presentation.main.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.diyarna.base.BaseFragment
import com.example.diyarna.data.remote.model.ProductItem
import com.example.diyarna.databinding.DetailsFragmentBinding
import com.example.diyarna.databinding.HomeFragmentBinding
import com.example.diyarna.domain.usecase.LoadPhoto
import com.example.diyarna.presentation.main.MainActivity

class DetailsFragment : BaseFragment<DetailsFragmentBinding>(DetailsFragmentBinding::inflate) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle("Details")

        var itemDto= requireArguments().getParcelable<ProductItem>("itemDetails")
        var loadImg=LoadPhoto()
        loadImg.invoke(binding.itemImage,itemDto!!.image,requireContext())
        binding.txtPrice.text=itemDto.price.toString()+" EGP"
        binding.txtname.text=itemDto.name
        binding.txtdescription.text=itemDto.description
    }

}