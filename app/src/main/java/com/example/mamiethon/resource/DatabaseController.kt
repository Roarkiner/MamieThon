package com.example.mamiethon.resource

import com.example.mamiethon.interfaces.IDatabaseController
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DatabaseController : IDatabaseController {
    private var database: FirebaseDatabase = Firebase.database("https://mamiethon-76438-default-rtdb.europe-west1.firebasedatabase.app/")

    override fun saveFavorite(recipeId: Int, userUid: String, enabled: Boolean) {
        database.getReference("users")
            .child(userUid)
            .child("favorites_recipes")
            .child(recipeId.toString())
            .setValue(enabled);
    }

    override fun getFavoritesForUser(userUid: String, callback: (List<String>) -> Unit) {
        val recipeFavoritesReference = database.getReference("users/$userUid/favorites_recipes")
        recipeFavoritesReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val recipeIds = snapshot.children
                    .filter { it.getValue(Boolean::class.java) == true }
                    .mapNotNull { it.key }
                callback(recipeIds)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    override fun checkIfRecipeIsFavoriteForUser(recipeId: Int, userUid: String, callback: (Boolean) -> Unit) {
        val recipeFavoritesReference = database.getReference("users/$userUid/favorites_recipes")

        recipeFavoritesReference.child(recipeId.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && snapshot.getValue(Boolean::class.java) == true) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }
}