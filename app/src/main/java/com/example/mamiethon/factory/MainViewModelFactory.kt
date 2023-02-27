package com.example.mamiethon.factory

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mamiethon.viewModel.MainViewModel

class MainViewModelFactory(private val parentActivity: Activity) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(parentActivity) as T
    }
}