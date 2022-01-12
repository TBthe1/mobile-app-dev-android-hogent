package com.example.android.nemesis.database.games

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_game_table")
data class Game(
    @PrimaryKey(autoGenerate = true)
    var gameId: Long = 0L,

    @ColumnInfo(name = "game_name")
    var gameName: String = "",

    @ColumnInfo(name = "game_desc")
    var gameDesc: String = "",

    @ColumnInfo(name = "game_min_duration")
    var gameMinDuration: String = "",

    @ColumnInfo(name = "game_max_duration")
    var gameMaxDuration: String = "",

    @ColumnInfo(name = "game_min_players")
    var gameMinPlayers: String = "",

    @ColumnInfo(name = "game_max_players")
    var gameMaxPlayers: String = ""
)