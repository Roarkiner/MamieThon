package com.example.mamiethon.resource

import com.example.mamiethon.interfaces.IDatabaseController
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DatabaseController : IDatabaseController {
    private var database: FirebaseDatabase = Firebase.database

    override fun saveFavorite(recipeId: Int, userUid: String) {
        database.getReference("users")
            .child(userUid)
            .child("favorites_recipes")
            .child(recipeId.toString())
            .setValue(true);
    }

    override fun getFavoritesForUser(userUid: String, callback: (List<String>) -> Unit) {
        val recipeFavoritesReference = database.getReference("users/$userUid/favorites")
        recipeFavoritesReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val recipeIds = snapshot.children.mapNotNull { it.getValue(String::class.java) }
                callback(recipeIds)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    override fun checkIfRecipeIsFavoriteForUser(recipeId: Int, userUid: String, callback: (Boolean) -> Unit) {
        val recipeFavoritesReference = database.getReference("users/$userUid/favorites")

        recipeFavoritesReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val favoriteRecipeId = childSnapshot.getValue(Int::class.java)
                    if (favoriteRecipeId == recipeId) {
                        callback(true)
                        return
                    }
                }
                callback(false)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }
}