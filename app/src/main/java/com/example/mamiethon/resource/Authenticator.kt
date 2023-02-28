package com.example.mamiethon.resource

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mamiethon.interfaces.IAuthenticator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Authenticator : IAuthenticator {
    private var auth: FirebaseAuth = Firebase.auth

    override fun checkIfUserIsConnected(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

    override fun connectUserWithEmailAndPassword(email: String, password: String, context: AppCompatActivity, completion: (success: Boolean?) -> Unit) {
        if(!email.isNullOrEmpty() && !password.isNullOrEmpty()){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(context) { task ->
                    if (task.isSuccessful) {
                        completion(true)
                    } else {
                        Toast.makeText(context, "Identifiant ou mot de passe incorrects.",
                            Toast.LENGTH_SHORT).show()
                        completion(null)
                    }
                }
        }  else {
            Toast.makeText(context, "Veuillez remplir tous les champs.",
                Toast.LENGTH_SHORT).show()
            completion(null)
        }
    }

    override fun createUserWithEmailAndPassword(email: String, password: String, confirmPassword: String, context: AppCompatActivity, completion: (success: Boolean?) -> Unit) {
        if (password != confirmPassword) {
            Toast.makeText(context, "Le mot de passe et la confirmation du mot de passe doivent être identiques.", Toast.LENGTH_SHORT).show()
        } else {
            if(!email.isNullOrEmpty() && !password.isNullOrEmpty() && !confirmPassword.isNullOrEmpty()){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(context) { task ->
                        if (task.isSuccessful) {
                            completion(true)
                        } else {
                            val error = task.exception
                            if(error is FirebaseAuthException && error.errorCode == "ERROR_WEAK_PASSWORD"){
                                Toast.makeText(
                                    context, "Le mot de passe n'est pas assez sécurisé.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if(error is FirebaseAuthException && error.errorCode == "ERROR_INVALID_EMAIL"){
                                Toast.makeText(
                                    context, "L'email est incorrect.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if(error is FirebaseAuthException && error.errorCode == "ERROR_EMAIL_ALREADY_IN_USE"){
                                Toast.makeText(
                                    context, "Le compte existe déjà.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context, "Une erreur est survenue lors de la création du compte.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            completion(null)
                        }
                    }
            }  else {
                Toast.makeText(context, "Veuillez remplir tous les champs.",
                    Toast.LENGTH_SHORT).show()
                completion(null)
            }
        }
    }

    override fun getCurrentUser(): FirebaseUser?
    {
        return Firebase.auth.currentUser
    }

    override fun logoutUser() {
        auth.signOut()
    }
}