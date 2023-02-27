package com.example.mamiethon.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mamiethon.data.LightRecipe
import com.example.mamiethon.data.RecipeRepository
import kotlinx.coroutines.launch

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {
    private val recipeRepository = RecipeRepository()
    private val context = getApplication<Application>().applicationContext

    private val _recipes = MutableLiveData<List<LightRecipe>>()
    val recipes: LiveData<List<LightRecipe>>
        get() = _recipes

    fun SearchRecipesByQuery(query: String, number: Int) {
        viewModelScope.launch {
            try {
                val result = recipeRepository.SearchRecipesByQuery(query, number)
                if (result != null)
                    _recipes.value = result!!
            } catch (e: java.lang.Exception) {
                Toast.makeText(
                    context, "Une erreur est survenue lors de la recherche de recette.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}