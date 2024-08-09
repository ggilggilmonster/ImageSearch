package com.example.imagesearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.MainActivity
import com.example.imagesearch.retrofit.SearchData
import com.example.imagesearch.databinding.SearchRecyclerviewBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class SearchAdapter(val mItems: MutableList<SearchData>) :
    RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    inner class ItemViewHolder(binding: SearchRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val thumbnails = binding.ivThumbnail
        val site = binding.tvSite
        val date = binding.tvDate

        fun bind(data: SearchData) {

            Glide.with(itemView.context)
                .load(data.thumbnailUrl).into(thumbnails)
            site.text = data.siteName
            date.text = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .format(OffsetDateTime.parse(data.dateTime))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            SearchRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    C

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = mItems[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
    }
}