package com.example.android.nemesis.screens.addGame

import android.app.Application
import androidx.lifecycle.*
import com.example.android.nemesis.database.games.Game
import com.example.android.nemesis.database.games.GameDatabaseDao
import kotlinx.coroutines.launch

class AddGameViewModel(val database: GameDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _saveEvent = MutableLiveData<Boolean>()
    val saveEvent: LiveData<Boolean>
        get() = _saveEvent

    init {
        _saveEvent.value = false
    }

    fun saveGameClick() {
        _saveEvent.value = true
    }

    fun saveEventDone() {
        _saveEvent.value = false
    }

    fun saveGame(newGame: String) {
        viewModelScope.launch {
            val game = Game()
            game.gameName = newGame
            saveGameToDatabase(game)
        }
    }

    // suspend methods
    suspend fun saveGameToDatabase(newGame: Game) {
        database.insert(newGame)
    }
}