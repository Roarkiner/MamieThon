package com.example.mamiethon.activity

import android.content.Intent
import android.graphics.ColorFilter
import android.opengl.Visibility
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mamiethon.R
import com.example.mamiethon.adapter.IngredientListAdapter
import com.example.mamiethon.adapter.RecipeListAdapter
import com.example.mamiethon.adapter.SimilarRecipeListAdapter
import com.example.mamiethon.data.LightRecipe
import com.example.mamiethon.data.SimilarRecipe
import com.example.mamiethon.interfaces.IRecipeListClickListener
import com.example.mamiethon.interfaces.ISimilarRecipeListClickListener
import com.example.mamiethon.viewModel.RecipeDetailViewModel
import com.squareup.picasso.Picasso

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var recipeDetailViewModel: RecipeDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val recipeId = intent.getIntExtra("recipeId", -1)
        recipeDetailViewModel = ViewModelProvider(this)[RecipeDetailViewModel::class.java]
        recipeDetailViewModel.setRecipeId(recipeId)

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
        val ingredientListAdapter = IngredientListAdapter()

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

            recipeInstructionsView.text = it.instructions
        })

        val similarRecipeRecyclerView = findViewById<RecyclerView>(R.id.recipe_similar_recipes)
        val noSimilarRecipeTextView = findViewById<TextView>(R.id.no_similar_recipes)
        val similarRecipeListAdapter = SimilarRecipeListAdapter(object : ISimilarRecipeListClickListener {
            override fun onSimilarRecipeClick(similarRecipe: SimilarRecipe) {
                val intent = Intent(this@RecipeDetailActivity, RecipeDetailActivity::class.java)
                intent.putExtra("recipeId", similarRecipe.id)
                startActivity(intent)
            }
        })
        similarRecipeRecyclerView.adapter = similarRecipeListAdapter
        similarRecipeRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        recipeDetailViewModel.similarRecipes.observe(this, Observer {
            if(it.isEmpty()){
                similarRecipeRecyclerView.visibility = RecyclerView.GONE
                noSimilarRecipeTextView.visibility = TextView.VISIBLE
            } else {
                noSimilarRecipeTextView.visibility = TextView.GONE
                similarRecipeRecyclerView.visibility = RecyclerView.VISIBLE
                similarRecipeListAdapter.setSimilarRecipes(it)
            }
        })

        recipeDetailViewModel.SearchRecipeDetail()
    }
}