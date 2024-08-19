package com.example.imagesearch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.MainActivity
import com.example.imagesearch.databinding.SearchRecyclerviewBinding
import com.example.imagesearch.model.SearchItemModel
import com.example.imagesearch.utils.Utils.getDateFromTimestampWithFormat

class SearchAdapter(var mContext: Context) : RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {

    var items = ArrayList<SearchItemModel>()

    fun clearItem() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            SearchRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

//    fun bind(data: SearchData) {
//
//        Glide.with(itemView.context)
//            .load(data.thumbnailUrl).into(thumbnails)
//        site.text = data.siteName
//        date.text = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//            .format(OffsetDateTime.parse(data.dateTime))
//        likes.bringToFront()
//    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]

        Glide.with(mContext)
            .load(currentItem.url)
            .into(holder.thumbnails)

        holder.likes.visibility = if (currentItem.isLike) View.VISIBLE else View.INVISIBLE
        holder.site.text = currentItem.title
        holder.date.text = getDateFromTimestampWithFormat(
            currentItem.dateTime,
            "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00", "yyyy-MM-dd HH:mm:ss"
        )
    }


//    interface ItemClick {
//        fun onClick(view: View, position: Int)
//    }

//    var itemClick: ItemClick? = null

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(binding: SearchRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        var thumbnails = binding.ivThumbnail
        var likes = binding.ivLikes
        var site = binding.tvSite
        var date = binding.tvDate
        var item = binding.clItem

        init {
            likes.visibility = View.GONE
            thumbnails.setOnClickListener(this)
            item.setOnClickListener(this)
        }


        // onClick을 interface가 아닌 override로 변경
        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return
            val item = items[position]

            item.isLike = !item.isLike

            if (item.isLike) {
                (mContext as MainActivity).addLikeItem(item)
            } else {
                (mContext as MainActivity).deleteLikeItem(item)
            }
            notifyItemChanged(position)
        }
    }
}