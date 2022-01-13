package com.example.android.nemesis.screens.addGame

import android.app.Application
import androidx.lifecycle.*
import com.example.android.nemesis.database.games.DatabaseGame
import com.example.android.nemesis.database.games.GameDatabase
import com.example.android.nemesis.database.games.GameDatabaseDao
import com.example.android.nemesis.domain.Game
import com.example.android.nemesis.repository.GameRepository
import kotlinx.coroutines.launch

class AddGameViewModel(val database: GameDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _saveEvent = MutableLiveData<Boolean>()
    val saveEvent: LiveData<Boolean>
        get() = _saveEvent

    private val db = GameDatabase.getInstance(application.applicationContext)
    private val repository = GameRepository(db)

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
            // saveGameToDatabase(game)
            saveGameWithRepository(game)
        }
    }

    // suspend methods
    suspend fun saveGameToDatabase(newGame: DatabaseGame) {
        database.insert(newGame)
    }

    suspend fun saveGameWithRepository(newGame: Game) {
        repository.createGame(newGame)
    }
}