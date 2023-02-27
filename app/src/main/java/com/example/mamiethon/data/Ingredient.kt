package com.example.mamiethon.data

import com.google.gson.annotations.SerializedName

data class Ingredient(
    val name: String,
    @SerializedName("image")
    val imageUrl: String,
    val amount: Double,
    val unit: String
)
