package com.example.android.nemesis.database.games

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface GameDatabaseDao {

    @Insert
    suspend fun insert(game: Game)

    @Update
    suspend fun update(game: Game)

    @Query("SELECT * from custom_game_table WHERE gameId = :key")
    suspend fun get(key: Long): Game?

    @Query("DELETE FROM custom_game_table")
    suspend fun clear()

    @Query("SELECT * FROM custom_game_table ORDER BY gameId DESC")
    suspend fun getAllGames(): List<Game>

    // get the game with the highest ID (last game added)
    @Query("SELECT * FROM custom_game_table ORDER BY gameId DESC LIMIT 1")
    suspend fun getLastGame(): Game?

    // get the number of games present
    @Query("SELECT COUNT(*) FROM custom_game_table")
    suspend fun numberOfGames(): Int
}
