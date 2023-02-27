package com.example.mamiethon.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mamiethon.R

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val idTextView = findViewById<TextView>(R.id.recipe_title)
        idTextView.text = intent.extras?.getString(EXTRA_RECIPE_ID) ?: "Chais po"
    }

    companion object {
        const val EXTRA_RECIPE_ID = "recipe_id"
    }
}