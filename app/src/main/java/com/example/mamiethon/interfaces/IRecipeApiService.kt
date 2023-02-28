package com.example.mamiethon.interfaces

import com.example.mamiethon.BuildConfig
import com.example.mamiethon.data.LightRecipesApiResult
import com.example.mamiethon.data.RecipeDetail
import com.example.mamiethon.data.SimilarRecipe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRecipeApiService {
    @GET("/recipes/complexSearch")
    suspend fun FetchRecipesByQuery(
        @Query("query") query: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ) : retrofit2.Response<LightRecipesApiResult>

    @GET("/recipes/{id}/information")
    suspend fun FetchRecipeById(
        @Path("id") recipeId: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ) : retrofit2.Response<RecipeDetail>

    @GET("/recipes/{id}/similar")
    suspend fun FetchSimilarRecipes(
        @Path("id") recipeId: Int,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ) : retrofit2.Response<List<SimilarRecipe>>

    @GET("/recipes/informationBulk")
    suspend fun FetchRecipesByIds(
        @Query("ids") commaSeparatedRecipeIds: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ) : retrofit2.Response<LightRecipesApiResult>
}