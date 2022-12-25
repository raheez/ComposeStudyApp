package com.app.imagelisting.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.imagelisting.model.UnsplashImage

@Dao
interface UnsplashImageDao {

    //when mentioning PagingSource<> as return type
    //it will paginate through Room data base
    @Query("SELECT * FROM unsplash_image_table")
     fun getAllImages():PagingSource<Int,UnsplashImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images:List<UnsplashImage>)

    @Query("DELETE FROM unsplash_image_table")
    suspend fun deleteAllImages()

}