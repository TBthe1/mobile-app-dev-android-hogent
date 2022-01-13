package com.example.android.nemesis.database.games

import androidx.lifecycle.LiveData
import androidx.room.*

/*
* Contains functions to insert and get games
* note: getGamesLive --> live data with a list of games
* note 2: insertAll --> takes a vararg games to update all games
*/

/*the Dao only knows about DatabaseGames*/

@Dao
interface GameDatabaseDao {

    @Insert
    suspend fun insert(game: DatabaseGame)

    // adding insert all with varar
    // replace strategy: upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg games: DatabaseGame)

    @Update
    suspend fun update(game: DatabaseGame)

    @Query("SELECT * from custom_game_table WHERE gameId = :key")
    suspend fun get(key: Long): DatabaseGame?

    @Query("DELETE FROM custom_game_table")
    suspend fun clear()

    @Query("SELECT * FROM custom_game_table ORDER BY gameId DESC")
    suspend fun getAllGames(): List<DatabaseGame>

    @Query("SELECT * FROM custom_game_table ORDER BY gameId DESC")
    fun getAllGamesLive(): LiveData<List<DatabaseGame>>

    // get the game with the highest ID (last game added)
    @Query("SELECT * FROM custom_game_table ORDER BY gameId DESC LIMIT 1")
    suspend fun getLastGame(): DatabaseGame?

    // get the number of games present
    @Query("SELECT COUNT(*) FROM custom_game_table")
    suspend fun numberOfGames(): Int
}
