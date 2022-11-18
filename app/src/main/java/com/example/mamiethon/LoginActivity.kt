package com.example.mamiethon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser == null)
            setContentView(R.layout.activity_login)
        else{
            switchToRecipeListActivity()
        }
    }

    private fun switchToRecipeListActivity(){
        val switchActivityIntent = Intent(this, RecipyList::class.java)
        startActivity(switchActivityIntent)
    }
}