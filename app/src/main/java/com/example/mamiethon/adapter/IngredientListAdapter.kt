package com.example.mamiethon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mamiethon.R
import com.example.mamiethon.data.Ingredient
import com.squareup.picasso.Picasso

class IngredientListAdapter() : RecyclerView.Adapter<IngredientListAdapter.IngredientListViewHolder>() {

    private val ingredients = mutableListOf<Ingredient>()

    class IngredientListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ingredientNameTextView: TextView = itemView.findViewById(R.id.ingredient_name)
        private val ingredientImageView: ImageView = itemView.findViewById(R.id.ingredient_image)
        private val ingredientAmountTextView: TextView = itemView.findViewById(R.id.ingredient_amount)

        fun bind(ingredient: Ingredient) {
            ingredientNameTextView.text = ingredient.name.replaceFirstChar{ it.uppercase() }
            Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + ingredient.imageUrl).into(ingredientImageView)
            ingredientAmountTextView.text = ingredient.amount.toString() + " " + ingredient.unit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return IngredientListViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientListViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int = ingredients.size

    fun setIngredients(newIngredients: List<Ingredient>) {
        ingredients.clear()
        ingredients.addAll(newIngredients)
        notifyDataSetChanged()
    }
}