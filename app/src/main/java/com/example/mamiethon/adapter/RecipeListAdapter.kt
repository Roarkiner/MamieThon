package com.example.mamiethon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mamiethon.R
import com.example.mamiethon.data.LightRecipe
import com.example.mamiethon.interfaces.IRecipeListClickListener
import com.squareup.picasso.Picasso

class RecipeListAdapter(private val listener: IRecipeListClickListener) : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {

    private val recipes = mutableListOf<LightRecipe>()

    class RecipeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(recipe: LightRecipe, listener: IRecipeListClickListener) {
            titleTextView.text = recipe.title
            Picasso.get().load(recipe.imageUrl).into(imageView)

            itemView.setOnClickListener { listener.onRecipeClick(recipe) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe, listener)
    }

    override fun getItemCount(): Int = recipes.size

    fun setRecipes(newRecipes: List<LightRecipe>) {
        recipes.clear()
        recipes.addAll(newRecipes)
        notifyDataSetChanged()
    }
}