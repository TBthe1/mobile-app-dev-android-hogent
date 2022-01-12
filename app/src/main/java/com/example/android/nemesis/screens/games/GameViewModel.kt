package com.example.android.nemesis.screens.games

import android.app.Application
import androidx.lifecycle.*
import com.example.android.nemesis.database.games.Game
import com.example.android.nemesis.database.games.GameDatabaseDao
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(val database: GameDatabaseDao, application: Application) : AndroidViewModel(application) {

    private lateinit var games: List<Game>
    private val numberOfGames = MutableLiveData<Int>()

    val numberOfGamesString = Transformations.map(numberOfGames) {
        number -> number.toString()
    }

    val buttonVisible = Transformations.map(numberOfGames) {
        it > 0
    }

    private val _currentGame = MutableLiveData<String>()
    val currentGame: LiveData<String>
        get() = _currentGame

    init {
        initializeLiveData()
    }

    private fun initializeLiveData() {
        viewModelScope.launch {
            numberOfGames.value = getNumberOfGamesFromDatabase()
            changeCurrentGames()
        }
    }

    fun changeCurrentGames() {
        // Check for evaluation:
        viewModelScope.launch {
            games = getAllGames()

            _currentGame.value = "Create your first game!"
            if (numberOfGames.value == null) return@launch

            // don't change the game if there are no games
            if (numberOfGames.value!! == 0) return@launch

            var randomListNumber = Random.nextInt(numberOfGames.value!!)

            // use the livedata game list to get a random game
            if (_currentGame.value == games.get(randomListNumber).gameName) randomListNumber =
                randomListNumber.plus(1).mod(numberOfGames.value!!)
            // use mod to stay in the correct range
            _currentGame.value = games.get(randomListNumber).gameName
        }
    }

    // Suspend functions
    private suspend fun getNumberOfGamesFromDatabase(): Int {
        return database.numberOfGames()
    }

    private suspend fun getAllGames(): List<Game> {
        return database.getAllGames()
    }
}