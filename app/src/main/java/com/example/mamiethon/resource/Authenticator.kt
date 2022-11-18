package com.example.mamiethon.resource

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mamiethon.activity.LoginActivity
import com.example.mamiethon.interfaces.IAuthenticator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await

class Authenticator : IAuthenticator {
    private var auth: FirebaseAuth = Firebase.auth

    override fun checkIfUserIsConnected(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

    override fun connectUserWithEmailAndPassword(email: String, password: String, context: AppCompatActivity, completion: (success: Boolean?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    completion(true)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    completion(null)
                }
            }
    }

    override fun createUserWithEmailAndPassword(email: String, password: String, context: AppCompatActivity, completion: (user: FirebaseUser?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(): FirebaseUser?
    {
        TODO("Not yet implemented")
    }

}