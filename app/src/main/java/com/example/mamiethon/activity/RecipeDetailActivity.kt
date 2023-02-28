package com.example.mamiethon.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mamiethon.R
import com.example.mamiethon.adapter.IngredientListAdapter
import com.example.mamiethon.adapter.SimilarRecipeListAdapter
import com.example.mamiethon.data.SimilarRecipe
import com.example.mamiethon.interfaces.ISimilarRecipeListClickListener
import com.example.mamiethon.viewModel.RecipeDetailViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var recipeDetailViewModel: RecipeDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val recipeId = intent.getIntExtra("recipeId", -1)
        recipeDetailViewModel = ViewModelProvider(this)[RecipeDetailViewModel::class.java]
        recipeDetailViewModel.setRecipeId(recipeId)
        recipeDetailViewModel.searchRecipeDetail()

        recipeDetailViewModel.terminateActivity.observe(this, Observer {
            if (it)
                finish()
        })
        val recipeTitleView = findViewById<TextView>(R.id.recipe_title)
        val recipeMainImageView = findViewById<ImageView>(R.id.recipe_image)
        val recipePrepTimeView = findViewById<TextView>(R.id.recipe_prep_time)
        val recipeSourceUrlView = findViewById<TextView>(R.id.recipe_source_url)
        val recipeIngredientsRecyclerView = findViewById<RecyclerView>(R.id.recipe_ingredients)
        val recipeVegetarianImageView = findViewById<ImageView>(R.id.recipe_vegetarian_image)
        val recipeVeganImageView = findViewById<ImageView>(R.id.recipe_vegan_image)
        val recipeInstructionsView = findViewById<TextView>(R.id.recipe_instructions)
        val recipeDescriptionView = findViewById<TextView>(R.id.recipe_description)
        val ingredientListAdapter = IngredientListAdapter()

        val favButton = findViewById<FloatingActionButton>(R.id.favButton)
        favButton.setOnClickListener {
            recipeDetailViewModel.saveFavoriteEnabledValue()
        }

        recipeDetailViewModel.isFav.observe(this, Observer {
            if(recipeDetailViewModel.isFav.value!!){
                favButton.setImageResource(R.drawable.ic_fav_filled)
                favButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            } else {
                favButton.setImageResource(R.drawable.ic_fav)
                favButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
            }
        })

        val shareButton = findViewById<FloatingActionButton>(R.id.shareButton)
        shareButton.setOnClickListener {
            startActivity(Intent.createChooser(recipeDetailViewModel.startShareActivity(), "Partager avec"))
        }

        recipeDetailViewModel.recipe.observe(this, Observer {
            recipeTitleView.text = it.title

            Picasso.get().load(it.imageUrl).into(recipeMainImageView)

            recipePrepTimeView.text = it.readyInMinutes.toString() + " min"

            recipeSourceUrlView.text = it.sourceUrl

            recipeIngredientsRecyclerView.adapter = ingredientListAdapter
            recipeIngredientsRecyclerView.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
            ingredientListAdapter.setIngredients(it.ingredients)

            if(!it.vegetarian){
                recipeVegetarianImageView.alpha = 0.2f
            }

            if(!it.vegan){
                recipeVeganImageView.alpha = 0.2f
            }

            val htmlFormattedInstructions = HtmlCompat.fromHtml(it.instructions, HtmlCompat.FROM_HTML_MODE_COMPACT)
            recipeInstructionsView.text = htmlFormattedInstructions

            val htmlFormattedDescription = HtmlCompat.fromHtml(it.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
            recipeDescriptionView.text = htmlFormattedDescription
        })

        val similarRecipeRecyclerView = findViewById<RecyclerView>(R.id.recipe_similar_recipes)
        val similarRecipeListAdapter = SimilarRecipeListAdapter(object : ISimilarRecipeListClickListener {
            override fun onSimilarRecipeClick(similarRecipe: SimilarRecipe) {
                val intent = Intent(this@RecipeDetailActivity, RecipeDetailActivity::class.java)
                intent.putExtra("recipeId", similarRecipe.id)
                startActivity(intent)
            }
        })
        similarRecipeRecyclerView.adapter = similarRecipeListAdapter
        similarRecipeRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}