package com.app.imagelisting.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.imagelisting.util.Constants.UNSPLASH_REMOTE_KEYS_TABLE

//to store pervious and next page keys,
// so that remote mediators knows which page to request next
@Entity(tableName = UNSPLASH_REMOTE_KEYS_TABLE)
data class UnsplashRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage:Int,
    val nextPage : Int
)