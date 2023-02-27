package com.example.mamiethon.interfaces

import com.example.mamiethon.BuildConfig
import com.example.mamiethon.data.LightRecipesApiResult
import com.example.mamiethon.data.RecipeDetailApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface IRecipeApiService {
    @GET("/recipes/complexSearch")
    suspend fun FetchRecipesByQuery(
        @Query("query") query: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ) : retrofit2.Response<LightRecipesApiResult>

    @GET("/recipes/{recipeId}/information")
    suspend fun FetchRecipeById(recipeId: Int) : retrofit2.Response<RecipeDetailApiResult>
}