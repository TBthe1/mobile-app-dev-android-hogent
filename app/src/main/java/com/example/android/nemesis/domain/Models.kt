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
    val gameUri = "https://cf.geekdo-images.com/wJfDIveg2zpTGn8E1WzEpA__imagepage/img/XatZTK6uZOSFgC_L706I94wydE4=/fit-in/900x600/filters:no_upscale():strip_icc()/pic3268473.jpg"
}