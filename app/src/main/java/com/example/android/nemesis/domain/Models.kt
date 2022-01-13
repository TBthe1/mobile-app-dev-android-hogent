// ktlint-disable filename
package com.example.android.nemesis.domain

/*
* Model class: this is how the game is known in the entire app
* */

data class Game(
    var gameId: Long = 0L,
    var gameName: String = "",
    var gameSubtype: String = ""
) {

    // Hardcoded image url to demo Glide
    val gameUri = "https://cf.geekdo-images.com/Z8yghP3v9BKvAALFWCziMw__thumb/img/OkqtAOk0Sd7IRlXlX8-2G0kS4Ng=/fit-in/200x150/filters:strip_icc()/pic3717830.png"
}