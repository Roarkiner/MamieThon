package com.example.mamiethon.viewModel

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mamiethon.data.RecipeDetail
import com.example.mamiethon.data.RecipeRepository
import com.example.mamiethon.data.SimilarRecipe
import com.example.mamiethon.interfaces.IAuthenticator
import com.example.mamiethon.resource.Authenticator
import com.example.mamiethon.resource.DatabaseController
import kotlinx.coroutines.launch

class RecipeDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val recipeRepository = RecipeRepository()
    private val databaseController = DatabaseController()
    private val authenticator: IAuthenticator = Authenticator()
    private val context = getApplication<Application>().applicationContext

    private val _terminateActivity = MutableLiveData(false)
    val terminateActivity: LiveData<Boolean>
        get() = _terminateActivity

    private val _recipeId = MutableLiveData(-1)
    fun setRecipeId(id: Int) {
        _recipeId.value = id
    }

    private val _isFav = MutableLiveData(false)
    val isFav: LiveData<Boolean>
        get() = _isFav

    private val _recipe = MutableLiveData<RecipeDetail>()
    val recipe: LiveData<RecipeDetail>
        get() = _recipe

    private val _similarRecipes = MutableLiveData<List<SimilarRecipe>>()
    val similarRecipes: LiveData<List<SimilarRecipe>>
        get() = _similarRecipes

    private fun getFavoriteEnabledValue() {
        val fireBaseUser = authenticator.getCurrentUser()
        if(fireBaseUser != null) {
            val userUid = fireBaseUser.uid

            databaseController.checkIfRecipeIsFavoriteForUser(_recipeId.value!!, userUid) {
                isFav ->
                    _isFav.value = isFav
            }
        }
    }

    fun saveFavoriteEnabledValue() {
        val fireBaseUser = authenticator.getCurrentUser()
        if(fireBaseUser != null) {
            val userUid = fireBaseUser.uid

            databaseController.saveFavorite(_recipeId.value!!, userUid, !_isFav.value!!)
            _isFav.value = !_isFav.value!!
        }
    }

    fun startShareActivity(): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/html"
        val shareRecipeTitle = _recipe.value?.title ?: ""
        val shareRecipeDescription = _recipe.value?.description ?: ""
        val htmlShareText = "<h2>$shareRecipeTitle</h2>$shareRecipeDescription"
        val htmlFormattedShareText = HtmlCompat.fromHtml(htmlShareText, HtmlCompat.FROM_HTML_MODE_COMPACT)
        shareIntent.putExtra(Intent.EXTRA_TEXT, htmlFormattedShareText)
        return  shareIntent
    }

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
                    getFavoriteEnabledValue()
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