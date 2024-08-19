package com.example.imagesearch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.databinding.SearchRecyclerviewBinding
import com.example.imagesearch.model.SearchItemModel
import com.example.imagesearch.utils.Utils

class MyStorageAdapter(var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = mutableListOf<SearchItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            SearchRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(mContext)
            .load(items[position].url)
            .into((holder as ItemViewHolder).thumbnails)

        holder.site.text = items[position].title
        holder.likes.visibility = View.GONE
        // 해설 코드엔 그냥 getDateFrom으로 하는데 안돼서 Utils에서 가져옴
        holder.date.text = Utils.getDateFromTimestampWithFormat(
            items[position].dateTime,
            "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
            "yyyy-MM-dd H H:mm:ss"
        )
    }

    inner class ItemViewHolder(binding: SearchRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var thumbnails: ImageView = binding.ivThumbnail
        var likes: ImageView = binding.ivLikes
        var site: TextView = binding.tvSite
        var date: TextView = binding.tvDate
        var item: ConstraintLayout = binding.clItem

        init {
            likes.visibility = View.GONE

            item.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }
}