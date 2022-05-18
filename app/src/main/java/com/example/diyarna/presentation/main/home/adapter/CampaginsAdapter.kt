package com.example.diyarna.presentation.main.home.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diyarna.data.remote.model.CampaignsItem
import com.example.diyarna.databinding.CampaginsItemBinding
import com.example.diyarna.databinding.HomeItemBinding
import com.example.diyarna.domain.usecase.LoadPhoto

class CampaginsAdapter: RecyclerView.Adapter<CampaginsAdapter.ItemHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<CampaignsItem>(){
        override fun areItemsTheSame(oldItem: CampaignsItem, newItem: CampaignsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CampaignsItem, newItem: CampaignsItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<CampaignsItem>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemBinding = CampaginsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ItemHolder(private val itemBinding: CampaginsItemBinding):RecyclerView.ViewHolder(itemBinding.root)
    {
        fun bind(itemDto: CampaignsItem){
            itemBinding.name.text=itemDto.name
            val loadPhoto = LoadPhoto()
            loadPhoto.invoke(itemBinding.itemImage,itemDto.image,itemView.context)
            itemBinding.valuesProgressBar.setValues(itemDto.actualAcmount.toFloat()+1000f,itemDto.requiredAmount.toFloat())
            itemBinding.addCart.setOnClickListener {
                if (mListener != null){
                    mListener.onItemClicked(itemDto)
                }
            }
        }
    }

    lateinit var mListener: ItemAdapterListener

    interface ItemAdapterListener {
        fun onItemClicked(item: CampaignsItem)
    }
}