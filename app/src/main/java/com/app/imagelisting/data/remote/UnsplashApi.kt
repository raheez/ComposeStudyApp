package com.app.imagelisting.data.remote

import com.app.imagelisting.BuildConfig
import com.app.imagelisting.model.UnsplashImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface UnsplashApi {

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/photos")
    suspend fun getAllImages(
        @Query("page") page:Int,
        @Query("per_page") per_page:Int):List<UnsplashImage>

}