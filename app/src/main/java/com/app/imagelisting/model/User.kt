package com.app.imagelisting.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable()
data class User(
    val name :String,
    @Embedded
    val links :Links)
