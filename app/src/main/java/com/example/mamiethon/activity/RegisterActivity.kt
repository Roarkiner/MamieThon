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

class RegisterActivity : AppCompatActivity() {
    private val authenticator: IAuthenticator = Authenticator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!authenticator.checkIfUserIsConnected()){
            setContentView(R.layout.activity_register)

            val registerButton = findViewById<Button>(R.id.createAccountButton)
            registerButton.setOnClickListener {
                tryToRegisterUser()
            }

            val registerLink = findViewById<TextView>(R.id.registerLink)
            registerLink.setOnClickListener {
                switchToLoginActivity()
            }
        } else {
            switchToMainActivity()
        }
    }

    private fun tryToRegisterUser(){
        var email = findViewById<EditText>(R.id.emailAddress).text.toString()
        var password = findViewById<EditText>(R.id.password).text.toString()
        var confirmPassword = findViewById<EditText>(R.id.confirmPassword).text.toString()
        authenticator.createUserWithEmailAndPassword(email, password, confirmPassword, this) { success ->
            if(success == true){
                switchToMainActivity()
            }
        }
    }

    private fun switchToMainActivity(){
        val switchActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(switchActivityIntent)
        finish()
    }

    private fun switchToLoginActivity() {
        val switchActivityIntent = Intent(this, LoginActivity::class.java)
        startActivity(switchActivityIntent)
    }
}