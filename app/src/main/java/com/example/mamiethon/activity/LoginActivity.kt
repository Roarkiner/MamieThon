package com.example.mamiethon.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.mamiethon.interfaces.IAuthenticator
import com.example.mamiethon.R
import com.example.mamiethon.resource.Authenticator

class LoginActivity : AppCompatActivity() {
    private val authenticator: IAuthenticator = Authenticator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!authenticator.checkIfUserIsConnected())
            setContentView(R.layout.activity_login)
        else{
            switchToRecipeListActivity()
        }

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            tryToConnectUser()
        }

        val registerLink = findViewById<TextView>(R.id.registerLink)
        registerLink.setOnClickListener {
            switchToRegisterActivity()
        }
    }

    private fun tryToConnectUser(){
        var email = findViewById<EditText>(R.id.emailAddress).text.toString()
        var password = findViewById<EditText>(R.id.password).text.toString()
        authenticator.connectUserWithEmailAndPassword(email, password, this) {
            success ->
                if(success == true){
                    switchToRecipeListActivity()
                }
        }
    }

    private fun switchToRecipeListActivity(){
        val switchActivityIntent = Intent(this, RecipeListActivity::class.java)
        startActivity(switchActivityIntent)
    }

    private fun switchToRegisterActivity() {
        val switchActivityIntent = Intent(this, RegisterActivity::class.java)
        startActivity(switchActivityIntent)
    }
}