package com.example.android.nemesis.screens.gameOverviewFromAPI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.nemesis.network.ApiGame
import com.example.android.nemesis.network.GameApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

enum class GameApiStatus { LOADING, ERROR, DONE }

class FromAPIViewModel : ViewModel() {

    private var _apiResponse = MutableLiveData<ApiGame>()
    val apiResponse: LiveData<ApiGame>
        get() = _apiResponse

    private val _status = MutableLiveData<GameApiStatus>()
    val status: LiveData<GameApiStatus>
        get() = _status

    init {
        Timber.i("getting games from the collection (API)")
        viewModelScope.launch {
            getGamesFromAPI()
        }
    }

    private suspend fun getGamesFromAPI() {
        _status.value = GameApiStatus.LOADING
        var getGamesDeferred = GameApi.retrofitService.getGames()

        try {
            var res = getGamesDeferred.await()
            _status.value = GameApiStatus.DONE
            _apiResponse.value = res
        } catch (e: Exception) {
            _status.value = GameApiStatus.ERROR
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}