package com.example.mamiethon.data

import com.google.gson.annotations.SerializedName

data class RecipeDetail(
    @SerializedName("id")
    val recipeId: Int,
    val title: String,
    @SerializedName("image")
    val imageUrl: String,
    val readyInMinutes: Int,
    val sourceUrl: String,
    @SerializedName("extendedIngredients")
    val ingredients: List<Ingredient>,
    val instructions: String,
    @SerializedName("summary")
    val description: String,
    val vegetarian: Boolean,
    val vegan: Boolean
)
