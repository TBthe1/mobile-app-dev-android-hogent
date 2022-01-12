package com.example.android.nemesis.database.games

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    abstract val gameDatabaseDao: GameDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getInstance(context: Context): GameDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameDatabase::class.java,
                        "custom_game_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}