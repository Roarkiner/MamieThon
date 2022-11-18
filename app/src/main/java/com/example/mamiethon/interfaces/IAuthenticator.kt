package com.example.mamiethon.interfaces

import com.google.firebase.auth.FirebaseUser

interface IAuthenticator {
    fun checkIfUserIsConnected(): Boolean
    fun connectUserWithEmailAndPassword(email: String, password: String)
    fun createUserWithEmailAndPassword(email: String, password: String)
    fun getCurrentUser(): FirebaseUser?
}