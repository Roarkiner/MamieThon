package com.example.mamiethon.interfaces

interface IDatabaseController {
    fun saveFavorite(recipeId: Int, userUid: String, enabled: Boolean)
    fun getFavoritesForUser(userUid: String, callback: (List<String>) -> Unit)
    fun checkIfRecipeIsFavoriteForUser(recipeId: Int, userId: String, callback: (Boolean) -> Unit)
}