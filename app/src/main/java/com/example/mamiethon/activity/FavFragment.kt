package com.example.mamiethon.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mamiethon.R
import com.example.mamiethon.adapter.RecipeListAdapter
import com.example.mamiethon.data.LightRecipe
import com.example.mamiethon.interfaces.IRecipeListClickListener
import com.example.mamiethon.viewModel.FavRecipeViewModel

class FavFragment : Fragment() {
    private lateinit var favRecipeViewModel: FavRecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favRecipeViewModel = ViewModelProvider(requireActivity())[FavRecipeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fav, container, false)

        val recipeListAdapter = RecipeListAdapter(object : IRecipeListClickListener {
            override fun onRecipeClick(recipe: LightRecipe) {
                val intent = Intent(requireActivity(), RecipeDetailActivity::class.java)
                intent.putExtra("recipeId", recipe.id)
                startActivity(intent)
            }
        })
        var recyclerView = view.findViewById<RecyclerView>(R.id.recipe_list_recycler_view)
        recyclerView.adapter = recipeListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        favRecipeViewModel.favRecipes.observe(viewLifecycleOwner, Observer { recipes ->
            recipeListAdapter.setRecipes(recipes)
        })

        return inflater.inflate(R.layout.fragment_fav, container, false)
    }
}