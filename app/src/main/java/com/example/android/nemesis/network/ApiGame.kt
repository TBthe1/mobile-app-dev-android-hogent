package com.example.android.nemesis.network

import com.example.android.nemesis.database.games.Game

data class ApiGame(

    val body: List<Game>
) {
    // Hardcoded image url to demo Glide
    val gameUri = "https://cf.geekdo-images.com/Z8yghP3v9BKvAALFWCziMw__thumb/img/OkqtAOk0Sd7IRlXlX8-2G0kS4Ng=/fit-in/200x150/filters:strip_icc()/pic3717830.png"
}