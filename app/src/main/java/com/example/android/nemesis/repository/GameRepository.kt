package com.example.android.nemesis.repository

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
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

    // What if the games list depends on query params?
    // --> The livedata source from the db will change!

    /* -- Solution 1 -- */
    /*MediatorLiveData
    * Hold a reference to the livedata instances, and switch them when needed
    * */

    /* -- Solution 2 -- */
    /*SwitchMap
    * Helper function for livedata (uses a MediatorLiveData object in the background)
    * */

    // Network call
    // get games from the database, but transform them with map
    val games = MediatorLiveData<List<Game>>()
    val filter = MutableLiveData<String>(null)
    val gamesSolution2 = Transformations.switchMap(filter) {
            filter -> when (filter) {
        "<10" -> Transformations.map(database.gameDatabaseDao.getUnder10GamesLive()) {
            it.asDomainModel()
        }
        "10-20" -> Transformations.map(database.gameDatabaseDao.getbetween1020GamesLive()) {
            it.asDomainModel()
        }
        ">20" -> Transformations.map(database.gameDatabaseDao.getgreater20GamesLive()) {
            it.asDomainModel()
        }
        else -> Transformations.map(database.gameDatabaseDao.getAllGamesLive()) {
            it.asDomainModel()
        }
    }
    }

    // keep a reference to the original livedata
    private var changeableLiveData = Transformations.map(database.gameDatabaseDao.getAllGamesLive()) {
        it.asDomainModel()
    }

    // add the data to the mediator
    init {
        games.addSource(
            changeableLiveData
        ) {
            games.setValue(it)
        }
    }

    // Filter
    fun addFilter(filter: String?) {
        // remove the original source
        games.removeSource(changeableLiveData)
        // change the livedata object + apply filter
        changeableLiveData = when (filter) {
            "<10" -> Transformations.map(database.gameDatabaseDao.getUnder10GamesLive()) {
                it.asDomainModel()
            }
            "10-20" -> Transformations.map(database.gameDatabaseDao.getbetween1020GamesLive()) {
                it.asDomainModel()
            }
            ">20" -> Transformations.map(database.gameDatabaseDao.getgreater20GamesLive()) {
                it.asDomainModel()
            }
            else -> Transformations.map(database.gameDatabaseDao.getAllGamesLive()) {
                it.asDomainModel()
            }
        }
        // add the data to the mediator
        games.addSource(changeableLiveData) { games.setValue(it) }
    }

    // filter is now less complex
    fun addFilterSolution2(filter: String?) {
        // remove the original source
        this.filter.value = filter
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
            gameSubtype = newGame.gameSubtype)
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