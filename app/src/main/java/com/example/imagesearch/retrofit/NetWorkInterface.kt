package com.example.imagesearch.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetWorkInterface {
    @Headers("Authorization: KakaoAK d04e4fc2760817de36e16ac5eaa03ad9")
    @GET("image")
    fun getImgData(
        @Query("query") query: String
    ): Call<ImageResponse>
}