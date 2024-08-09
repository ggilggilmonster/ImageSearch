package com.example.imagesearch.retrofit

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    val documents: MutableList<SearchData>,
)

data class SearchData(
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("display_sitename")
    val siteName: String,
    @SerializedName("datetime")
    val dateTime: String
)
