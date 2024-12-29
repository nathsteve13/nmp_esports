package com.ubaya.nmp_esports

data class Game(
    val idgame: Int,
    val name: String,
    val description: String,
    val imageUrl: String = ""
)
