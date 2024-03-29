package com.example.mamiethon.viewModel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mamiethon.activity.LoginActivity
import com.example.mamiethon.interfaces.IAuthenticator
import com.example.mamiethon.resource.Authenticator
import kotlinx.coroutines.launch

class MainViewModel(private val parentActivity: Activity) : ViewModel() {
    private val authenticator: IAuthenticator = Authenticator()

    fun disconnectAndClearActivity(){
        authenticator.logoutUser()

        viewModelScope.launch {
            val intent = Intent(parentActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            parentActivity.startActivity(intent)
            parentActivity.finish()
        }
    }
}