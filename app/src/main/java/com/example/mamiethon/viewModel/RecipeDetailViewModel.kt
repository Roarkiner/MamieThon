package com.example.mamiethon.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mamiethon.data.RecipeDetail
import com.example.mamiethon.data.RecipeRepository
import com.example.mamiethon.data.SimilarRecipe
import kotlinx.coroutines.launch

class RecipeDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val recipeRepository = RecipeRepository()
    private val context = getApplication<Application>().applicationContext

    private val _terminateActivity = MutableLiveData(false)
    val terminateActivity: LiveData<Boolean>
        get() = _terminateActivity

    private val _recipeId = MutableLiveData(-1)
    fun setRecipeId(id: Int) {
        _recipeId.value = id
    }

    private val _recipe = MutableLiveData<RecipeDetail>()
    val recipe: LiveData<RecipeDetail>
        get() = _recipe

    private val _similarRecipes = MutableLiveData<List<SimilarRecipe>>()
    val similarRecipes: LiveData<List<SimilarRecipe>>
        get() = _similarRecipes

    fun searchRecipeDetail() {
        searchRecipeById()
        searchSimilarRecipes()
    }

    private fun searchRecipeById() {
        viewModelScope.launch {
            try {
                val result = recipeRepository.searchRecipeById(_recipeId.value!!)
                if (result != null) {
                    _recipe.value = result!!
                } else {
                    throw java.lang.Exception()
                }
            } catch (e: java.lang.Exception) {
                Log.d("SearchRecipeByIdVM", e.message.toString())
                Toast.makeText(
                    context, "Une erreur est survenue lors de l'affichage de la recette.",
                    Toast.LENGTH_SHORT
                ).show()
                _terminateActivity.value = true
            }
        }
    }

    private fun searchSimilarRecipes() {
        viewModelScope.launch {
            try {
                val result = recipeRepository.searchSimilarRecipes(_recipeId.value!!, 3)
                if (result != null ) {
                    _similarRecipes.value = result!!
                } else {
                    throw java.lang.Exception()
                }
            } catch (e: java.lang.Exception) {
                Log.d("Oui", e.message.toString())
                Toast.makeText(
                    context, "Une erreur est survenue lors de l'affichage des recettes similaires.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}