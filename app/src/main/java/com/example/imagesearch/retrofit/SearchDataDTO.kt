package com.example.imagesearch.retrofit

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("documents")
    val documents: MutableList<Documents>,

    @SerializedName("meta")
    val meta: Meta
) {

    data class Documents(
        @SerializedName("thumbnail_url")
        var thumbnailUrl: String,
        @SerializedName("display_sitename")
        var siteName: String,
        @SerializedName("datetime")
        var dateTime: String,
    )

    data class Meta(
        @SerializedName("is_end")
        val isEnd: Boolean,

        @SerializedName("pageable_count")
        val pageableCount: Int,

        @SerializedName("total_count")
        val totalCount: Int
    )
}