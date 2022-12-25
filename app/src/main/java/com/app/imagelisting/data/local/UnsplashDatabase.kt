package com.app.imagelisting.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.imagelisting.data.local.dao.UnsplashImageDao
import com.app.imagelisting.data.local.dao.UnsplashRemoteKeyDao
import com.app.imagelisting.model.UnsplashImage
import com.app.imagelisting.model.UnsplashRemoteKeys

@Database(entities = [UnsplashImage::class,UnsplashRemoteKeys::class], version = 1)
abstract class UnsplashDatabase :RoomDatabase(){

    abstract fun unsplashImageDao():UnsplashImageDao
    abstract fun unsplashRemoteKeyDao():UnsplashRemoteKeyDao

}