package com.example.android.nemesis.screens.gameOverview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.nemesis.database.games.GameDatabaseDao

class GameOverviewViewModel(val database: GameDatabaseDao, app: Application) : AndroidViewModel(app) {
    val games = database.getAllGamesLive()
}