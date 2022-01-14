package com.example.android.nemesis.database.games

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.nemesis.domain.Game
import com.squareup.moshi.Json

/*
* Database entity DatabaseGame
* This represents a Game in the database
* it also has a function to transform a list of database games
* to the games used in the rest of the app: model games
* */

@Entity(tableName = "custom_game_table")
data class DatabaseGame(
    @PrimaryKey(autoGenerate = true)
    var gameId: Long = 0L,

    @ColumnInfo(name = "game_name")
    @Json(name = "gameName") var gameName: String = "",

    @ColumnInfo(name = "game_subtype")
    @Json(name = "gameSubtype") var gameSubtype: String = ""
)

// convert Game to ApiGame
fun List<DatabaseGame>.asDomainModel(): List<Game> {
    return map {
        Game(
            gameName = it.gameName,
            gameSubtype = it.gameSubtype
        )
    }
}
