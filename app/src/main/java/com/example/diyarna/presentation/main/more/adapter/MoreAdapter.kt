package com.example.diyarna.presentation.main.more.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diyarna.R
import com.example.diyarna.data.local.model.MoreModel
import com.example.diyarna.data.remote.model.ProductItem
import com.example.diyarna.databinding.CartItemBinding
import com.example.diyarna.databinding.MoreItemBinding
import com.example.diyarna.presentation.main.cart.adapter.CartAdapter

class MoreAdapter : RecyclerView.Adapter<MoreAdapter.ItemHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<MoreModel>(){
        override fun areItemsTheSame(oldItem: MoreModel, newItem: MoreModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: MoreModel, newItem: MoreModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<MoreModel>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemBinding = MoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item,position);
    }

    inner class ItemHolder(private val itemBinding: MoreItemBinding):RecyclerView.ViewHolder(itemBinding.root)
    {
        @SuppressLint("SetTextI18n")
        fun bind(itemDto: MoreModel,position: Int){
            itemBinding.name.text=itemView.context.getString(itemDto.name)
            itemBinding.icon.setImageResource(itemDto.resource)
            itemBinding.card.setOnClickListener {
                mListener.onClickItem(position,itemDto)
            }
        }
    }

    lateinit var mListener: ItemAdapterListener

    interface ItemAdapterListener {

        fun onClickItem(position: Int,itemDto: MoreModel)

    }
}