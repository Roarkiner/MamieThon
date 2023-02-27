package com.example.mamiethon.interfaces

import com.example.mamiethon.data.LightRecipe

interface IRecipeListClickListener {
    fun onRecipeClick(recipe: LightRecipe)
}