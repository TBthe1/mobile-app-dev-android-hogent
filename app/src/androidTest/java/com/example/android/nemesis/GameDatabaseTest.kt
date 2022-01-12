package com.example.android.nemesis

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import com.example.android.nemesis.database.games.Game
import com.example.android.nemesis.database.games.GameDatabase
import com.example.android.nemesis.database.games.GameDatabaseDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class GameDatabaseTest {

    private lateinit var gameDao: GameDatabaseDao
    private lateinit var db: GameDatabase

    @Before
    fun createDb() {
        Log.i("before", "running before")
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, GameDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        gameDao = db.gameDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetJoke() = runBlocking {
        val game = Game()
        gameDao.insert(game)
        val lastGame = gameDao.getLastGame()
        assertEquals(lastGame?.gameName, "")
    }
}