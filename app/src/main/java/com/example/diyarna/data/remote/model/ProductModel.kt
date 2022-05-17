package com.example.diyarna.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class ProductModel(
	val product: List<ProductItem>
)


@Parcelize
class Products: ArrayList<ProductItem>(), Parcelable

@Parcelize
data class ProductItem(
    val image: String,
    var price: Double,
    val name: String,
    val description: String,
    val id: Int,
    var count:Int=0,
    var totalPrice:Double=0.0
): Parcelable

