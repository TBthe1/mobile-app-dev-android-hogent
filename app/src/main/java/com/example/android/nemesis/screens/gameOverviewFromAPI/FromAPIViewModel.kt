package com.example.android.nemesis.screens.gameOverviewFromAPI

import android.app.Application
import androidx.lifecycle.*
import com.example.android.nemesis.database.games.GameDatabase
import com.example.android.nemesis.repository.GameRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

enum class GameApiStatus { LOADING, ERROR, DONE }

class FromAPIViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<GameApiStatus>()
    val status: LiveData<GameApiStatus>
        get() = _status

    private val database = GameDatabase.getInstance(application.applicationContext)
    private val gameRepository = GameRepository(database)

    val games = gameRepository.games

    init {

        Timber.i("getting games")
        viewModelScope.launch {
            _status.value = GameApiStatus.LOADING
            gameRepository.refreshGames()
            _status.value = GameApiStatus.DONE
        }
    }

    // No longer needed: the repository will take care of this

    /*private suspend fun getGamesFromAPI() {
        _status.value = GameApiStatus.LOADING
        var getGamesDeferred = GameApi.retrofitService.getGames()

        try {
            var res = getGamesDeferred.await()
            _status.value = GameApiStatus.DONE
            _apiResponse.value = res
        } catch (e: Exception) {
            _status.value = GameApiStatus.ERROR
        }
    }*/

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    // Factory for constructing FromAPIViewModel with parameter
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FromAPIViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FromAPIViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}