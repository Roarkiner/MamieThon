package com.example.mamiethon.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mamiethon.data.LightRecipe
import com.example.mamiethon.data.RecipeRepository
import com.example.mamiethon.interfaces.IAuthenticator
import com.example.mamiethon.resource.Authenticator
import com.example.mamiethon.resource.DatabaseController
import kotlinx.coroutines.launch

class FavRecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val recipeRepository = RecipeRepository()
    private val context = getApplication<Application>().applicationContext
    private val databaseController = DatabaseController()
    private val authenticator: IAuthenticator = Authenticator()
    private var favoritesIdsList: List<String> = emptyList()

    private val _favRecipes = MutableLiveData<List<LightRecipe>>()
    val favRecipes: LiveData<List<LightRecipe>>
        get() = _favRecipes

    fun GetFavoriteRecipes() {
        val fireBaseUser = authenticator.getCurrentUser()
        if(fireBaseUser != null) {
            val userUid = fireBaseUser.uid

            databaseController.getFavoritesForUser(userUid) {
                favorites ->
                    favoritesIdsList = favorites
            }
        }

        viewModelScope.launch {
            try {
                val result = recipeRepository.searchRecipesByIds(favoritesIdsList)
                if (result != null) {
                    _favRecipes.value = result!!
                } else {
                    throw java.lang.Exception()
                }
            } catch (e: java.lang.Exception) {
                Toast.makeText(
                    context, "Une erreur est survenue lors de l'affichage des favoris.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}