package com.example.mamiethon.data

import com.google.gson.annotations.SerializedName

data class LightRecipesApiResult(
    @SerializedName("results")
    val recipes: List<LightRecipe>
)
