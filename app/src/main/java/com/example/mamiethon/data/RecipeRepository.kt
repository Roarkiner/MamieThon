package com.example.mamiethon.data

import com.example.mamiethon.interfaces.IRecipeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeRepository {
    private val baseUrl = "https://api.spoonacular.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val recipeApiService = retrofit.create(IRecipeApiService::class.java)

    suspend fun searchRecipesByQuery(query: String, number: Int): List<LightRecipe>? {
        val response = recipeApiService.fetchRecipesByQuery(query, number)
        return if(response.isSuccessful && response.body() != null) {
            val lightRecipesApiResult = response.body()!!
            lightRecipesApiResult.recipes
        } else {
            null
        }
    }

    suspend fun searchRecipeById(recipeId: Int): RecipeDetail? {
        val response = recipeApiService.fetchRecipeById(recipeId)
        return if(response.isSuccessful && response.body() != null) {
            val recipeDetailApiResult = response.body()!!
            recipeDetailApiResult
        } else {
            null
        }
    }

    suspend fun searchSimilarRecipes(recipeId: Int, numberOfSimilarRecipes: Int): List<SimilarRecipe>? {
        val response = recipeApiService.fetchSimilarRecipes(recipeId, numberOfSimilarRecipes)
        return if(response.isSuccessful && response.body() != null) {
            val similarRecipesListApiResult = response.body()!!
            similarRecipesListApiResult
        } else {
            null
        }
    }

    suspend fun searchRecipesByIds(recipeIdsList: List<String>) : List<LightRecipe>?{
        val commaSeparatedRecipeIds = recipeIdsList.joinToString(separator = ",")
        val response = recipeApiService.fetchRecipesByIds(commaSeparatedRecipeIds)
        return if(response.isSuccessful) {
            if(response.body() != null) {
                val lightRecipesList = response.body()!!
                lightRecipesList
            } else {
                emptyList()
            }
        } else {
            null
        }
    }
}