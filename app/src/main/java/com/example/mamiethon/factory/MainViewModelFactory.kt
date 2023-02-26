package com.example.mamiethon.factory

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mamiethon.viewmodel.MainViewModel

class MainViewModelFactory(private val parentActivity: Activity) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(parentActivity) as T
    }
}