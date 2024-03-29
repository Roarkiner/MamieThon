package com.example.mamiethon.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mamiethon.R
import com.example.mamiethon.adapter.RecipeListAdapter
import com.example.mamiethon.data.LightRecipe
import com.example.mamiethon.interfaces.IRecipeListClickListener
import com.example.mamiethon.viewModel.RecipeListViewModel

class RecipeListFragment : Fragment() {
    private lateinit var recipeListViewModel : RecipeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipeListViewModel = ViewModelProvider(requireActivity())[RecipeListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

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

        recipeListViewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            recipeListAdapter.setRecipes(recipes)
        })

        view.findViewById<SearchView>(R.id.search_view).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                recipeListViewModel.searchRecipesByQuery(query, 20)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return view
    }
}