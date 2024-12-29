package com.ubaya.nmp_esports

data class DetailTeam(
    var idTeam: Int,
    var teamName: String,
    var members: List<String>,
    var roles: List<String>
)
