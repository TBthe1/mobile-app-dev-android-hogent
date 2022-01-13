package com.example.android.nemesis.network

import com.example.android.nemesis.database.games.DatabaseGame
import com.example.android.nemesis.domain.Game
import com.squareup.moshi.Json

/*ApiGame: this is a DataTransferObject
* it's goal is to get network data into our model data, the Game
*/

/*Container helps us parse the body into multiple games*/
data class ApiGameContainer(
    @Json(name = "body")
    val apiGames: List<ApiGame>
)

/*ApiGame, representing a game from the network*/
data class ApiGame(
    @Json(name = "name")
    var gameName: String = "",

    @Json(name = "subtype")
    var gameSubtype: String = ""
)

/*
* Convert network results into Domain games
* */
fun ApiGameContainer.asDomainModel(): List<Game> {
    return apiGames.map {
        Game(
            gameName = it.gameName,
            gameSubtype = it.gameSubtype
        )
    }
}

/*
* Convert network result into Database games
*
* returns an array that can be used in the insert call as vararg
* */
fun ApiGameContainer.asDatabaseModel(): Array<DatabaseGame> {
    return apiGames.map {
        DatabaseGame(gameName = it.gameName,
            gameSubtype = it.gameSubtype)
    }.toTypedArray()
}

/*
* Convert a single api game to a database game
* */
fun ApiGame.asDatabaseGame(): DatabaseGame {
    return DatabaseGame(
        gameName = gameName,
        gameSubtype = gameSubtype
    )
}