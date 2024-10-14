package com.ubaya.nmp_esports

data class DetailTeam(
    var idTeam: Int,
    var teamName: String,
    var members: Array<String>,
    var roles: Array<String>
)
