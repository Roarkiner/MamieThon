package com.example.mamiethon.data

import com.example.mamiethon.interfaces.IRecipeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeRepository {
    private val BASE_URL = "https://api.spoonacular.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val recipeApiService = retrofit.create(IRecipeApiService::class.java)

    suspend fun SearchRecipesByQuery(query: String, number: Int): List<LightRecipe>? {
        val response = recipeApiService.FetchRecipesByQuery(query, number)
        return if(response.isSuccessful && response.body() != null) {
            val lightRecipesApiResult = response.body()!!
            lightRecipesApiResult.recipes
        } else {
            null
        }
    }
}