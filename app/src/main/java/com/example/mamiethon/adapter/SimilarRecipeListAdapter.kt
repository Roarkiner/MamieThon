package com.example.mamiethon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mamiethon.R
import com.example.mamiethon.data.SimilarRecipe
import com.example.mamiethon.interfaces.IRecipeListClickListener
import com.example.mamiethon.interfaces.ISimilarRecipeListClickListener
import com.squareup.picasso.Picasso

class SimilarRecipeListAdapter(private val listener: ISimilarRecipeListClickListener) : RecyclerView.Adapter<SimilarRecipeListAdapter.SimilarRecipeListViewHolder>() {

    private val similarRecipes = mutableListOf<SimilarRecipe>()

    class SimilarRecipeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)

        fun bind(similarRecipe: SimilarRecipe, listener: ISimilarRecipeListClickListener) {
            titleTextView.text = similarRecipe.title

            itemView.setOnClickListener { listener.onSimilarRecipeClick(similarRecipe) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarRecipeListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return SimilarRecipeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimilarRecipeListViewHolder, position: Int) {
        val recipe = similarRecipes[position]
        holder.bind(recipe, listener)
    }

    override fun getItemCount(): Int = similarRecipes.size

    fun setSimilarRecipes(newSimilarRecipes: List<SimilarRecipe>) {
        similarRecipes.clear()
        similarRecipes.addAll(newSimilarRecipes)
        notifyDataSetChanged()
    }
}