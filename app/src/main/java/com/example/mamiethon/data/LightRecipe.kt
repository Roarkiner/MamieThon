package com.example.mamiethon.data

import com.google.gson.annotations.SerializedName

data class LightRecipe(
    val id: Int,
    val title: String,
    @SerializedName("image")
    val imageUrl: String,
)
