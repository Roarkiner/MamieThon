package com.example.mamiethon.interfaces

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser

interface IAuthenticator {
    fun checkIfUserIsConnected(): Boolean
    fun connectUserWithEmailAndPassword(email: String, password: String, context: AppCompatActivity, completion: (success: Boolean?) -> Unit)
    fun createUserWithEmailAndPassword(email: String, password: String, context: AppCompatActivity, completion: (user: FirebaseUser?) -> Unit)
    fun getCurrentUser(): FirebaseUser?
}