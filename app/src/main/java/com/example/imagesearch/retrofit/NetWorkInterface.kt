package com.example.imagesearch.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetWorkInterface {
    @GET("image")
    fun getImgData(
        @Header("Authorization") apiKey: String?,
        @Query("query") query: String,
        @Query("sort") sort: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<ImageResponse>?
}