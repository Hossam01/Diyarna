package com.example.diyarna.presentation.main.products.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diyarna.data.remote.model.ProductItem
import com.example.diyarna.databinding.HomeItemBinding
import com.example.diyarna.databinding.ProductItemBinding
import com.example.diyarna.domain.usecase.LoadPhoto

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ItemHolder>()  {


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

        val itemBinding = HomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ItemHolder(private val itemBinding: HomeItemBinding):RecyclerView.ViewHolder(itemBinding.root)
    {
        fun bind(itemDto: ProductItem){
            itemBinding.name.text=itemDto.name
            itemBinding.description.text=itemDto.description
            val loadPhoto = LoadPhoto()
            loadPhoto.invoke(itemBinding.itemImage,itemDto.image,itemView.context)

            if(itemDto.count>0)
            {
                itemBinding.addCartLayout.root.visibility=View.VISIBLE
                itemBinding.cardlayout.visibility=View.GONE
                itemBinding.addCartLayout.cartCountIV.setText(""+itemDto.count)

            }

            itemBinding.addCart.setOnClickListener {
                if (mListener != null){
                    itemDto.count+=1
                    itemBinding.addCartLayout.cartCountIV.setText(""+itemDto.count)
                    itemBinding.addCartLayout.root.visibility=View.VISIBLE
                    itemBinding.cardlayout.visibility= View.GONE
                    mListener.onItemClicked(itemDto)
                }
            }

            itemBinding.card.setOnClickListener {
                if (mListener != null){
                    mListener.onDetailsItemClicked(itemDto)
                }
            }

            itemBinding.addCartLayout.increase.setOnClickListener {
                itemDto.count+=1
                itemBinding.addCartLayout.cartCountIV.setText(""+itemDto.count)
                mListener.updateItem(itemDto)
            }

            itemBinding.addCartLayout.decrease.setOnClickListener {
                itemDto.count-=1
                itemBinding.addCartLayout.cartCountIV.setText(""+itemDto.count)
                mListener.updateItem(itemDto)
                if (itemDto.count==0)
                {
                    itemBinding.addCartLayout.root.visibility=View.GONE
                    itemBinding.cardlayout.visibility=View.VISIBLE
                }
            }
        }
    }

    lateinit var mListener: ItemAdapterListener

    interface ItemAdapterListener {
        fun onItemClicked(item: ProductItem)
        fun onDetailsItemClicked(item: ProductItem)
        fun onItemRemoved(item: ProductItem)
        fun updateItem(item: ProductItem)
    }
}