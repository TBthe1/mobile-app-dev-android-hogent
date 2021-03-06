package com.example.android.nemesis.screens.addGame

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.nemesis.database.games.GameDatabaseDao

class AddGameViewModelFactory(private val dataSource: GameDatabaseDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddGameViewModel::class.java)) {
            return AddGameViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}