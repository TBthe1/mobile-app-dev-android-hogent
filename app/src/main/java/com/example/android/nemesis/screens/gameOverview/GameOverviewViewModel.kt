package com.example.android.nemesis.screens.gameOverview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.nemesis.database.games.GameDatabase
import com.example.android.nemesis.database.games.GameDatabaseDao
import com.example.android.nemesis.repository.GameRepository

class GameOverviewViewModel(val database: GameDatabaseDao, app: Application) : AndroidViewModel(app) {

    private var currentFilter: String? = null

    val db = GameDatabase.getInstance(app.applicationContext)
    val repository = GameRepository(db)

    val games = repository.gamesSolution2

    fun filterChip(changedFilter: String, isChecked: Boolean) {
        // set currentFilter
        if (isChecked) {
            currentFilter = changedFilter
        } else if (currentFilter == changedFilter) {
            currentFilter = null
        }

        // repository.addFilter(currentFilter)
        repository.addFilterSolution2(currentFilter)
    }
}