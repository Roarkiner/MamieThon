package com.example.mamiethon.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mamiethon.R
import com.example.mamiethon.factory.MainViewModelFactory
import com.example.mamiethon.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val recipeListFragment = RecipeListFragment()
    private val favFragment = FavFragment()
    private val profileFragment = ProfileFragment()
    private var activeFragment: Fragment = recipeListFragment
    private val fragmentManager = supportFragmentManager
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(this))[MainViewModel::class.java]

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { selectedItem ->
            when (selectedItem.itemId) {
                R.id.nav_search_page -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(recipeListFragment).commit()
                    activeFragment = recipeListFragment
                    true
                }
                R.id.nav_fav_page -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(favFragment).commit()
                    activeFragment = favFragment
                    true
                }
                R.id.nav_profile_page -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit()
                    activeFragment = profileFragment
                    true
                }
                else -> false
            }
        }

        fragmentManager.beginTransaction().add(R.id.main_content_container, profileFragment, "profile").hide(profileFragment).commit()
        fragmentManager.beginTransaction().add(R.id.main_content_container, favFragment, "fav").hide(favFragment).commit()
        fragmentManager.beginTransaction().add(R.id.main_content_container, recipeListFragment, "recipeList").commit()
    }
}