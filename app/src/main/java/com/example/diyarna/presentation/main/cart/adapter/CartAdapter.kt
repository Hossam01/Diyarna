package com.example.diyarna.presentation.main.cart.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diyarna.data.remote.model.ProductItem
import com.example.diyarna.databinding.CartItemBinding
import com.example.diyarna.databinding.ProductItemBinding
import com.example.diyarna.domain.usecase.LoadPhoto

class CartAdapter : RecyclerView.Adapter<CartAdapter.ItemHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ProductItem>(){
        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<ProductItem>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemBinding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item);
    }

    inner class ItemHolder(private val itemBinding: CartItemBinding):RecyclerView.ViewHolder(itemBinding.root)
    {
        @SuppressLint("SetTextI18n")
        fun bind(itemDto: ProductItem){
            itemBinding.name.text=itemDto.name
            val loadPhoto = LoadPhoto()
            loadPhoto.invoke(itemBinding.itemImage,itemDto.image,itemView.context)
            itemBinding.count.cartCountIV.setText(""+itemDto.count)
            itemBinding.price.text=""+itemDto.price*itemDto.count+" EGP"
        }
    }

    lateinit var mListener: ItemAdapterListener

    interface ItemAdapterListener {
        fun onItemClicked(item: ProductItem)
        fun onDetailsItemClicked(item: ProductItem)

    }

}