package com.example.android.nemesis.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.nemesis.database.games.GameDatabase
import com.example.android.nemesis.database.games.asDomainModel
import com.example.android.nemesis.domain.Game
import com.example.android.nemesis.network.ApiGame
import com.example.android.nemesis.network.GameApi
import com.example.android.nemesis.network.GameApi.mockPutGame
import com.example.android.nemesis.network.asDatabaseGame
import com.example.android.nemesis.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/*
* Class to connect the db and the network
* Contains a LiveData object with games
* Can refresh games
* */

class GameRepository(private val database: GameDatabase) {

    // Network call
    // get games from the database, but transform them with map
    val games: LiveData<List<Game>> =
        Transformations.map(database.gameDatabaseDao.getAllGamesLive()) {
            it.asDomainModel()
    }

    // Database call
    suspend fun refreshGames() {
        // switch context to IO thread
        withContext(Dispatchers.IO) {
            val games = GameApi.retrofitService.getGames().await()
            // '*': kotlin spread operator.
            // Used for functions that expect a vararg param
            // just spreads the array into separate fields
            database.gameDatabaseDao.insertAll(*games.asDatabaseModel())
            Timber.i("end suspend")
        }
    }

    // create a new game + return the resulting game
    suspend fun createGame(newGame: Game): Game {
        // create a Data Transfer Object (Dto)
        val newApiGame = ApiGame(
            gameName = newGame.gameName,
            gameSubtype = newGame.gameSubtype
        )
        // use retrofit to put the game.
        // a put function usually returns the object that was put

        // val checkApiGame = GameApi.retrofitService.putGame(newApiGame).await()
        val checkApiGame = GameApi.retrofitService.mockPutGame(newApiGame)

        // the put results in a GameApi object
        // when the put is done, update the local db as well
        database.gameDatabaseDao.insert(checkApiGame.asDatabaseGame())
        // the returned game can be used to pass as save arg to the next fragment (e.g)
        return newGame
    }
}