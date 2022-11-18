package com.example.mamiethon.resource

import com.example.mamiethon.interfaces.IAuthenticator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Authenticator : IAuthenticator {
    private var auth: FirebaseAuth = Firebase.auth

    override fun checkIfUserIsConnected(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

    override fun connectUserWithEmailAndPassword(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun createUserWithEmailAndPassword(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(): FirebaseUser?
    {
        TODO("Not yet implemented")
    }

}