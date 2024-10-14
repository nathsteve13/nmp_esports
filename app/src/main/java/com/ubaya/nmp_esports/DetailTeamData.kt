package com.ubaya.nmp_esports

object DetailTeamData {
    var detailTeam: Array<DetailTeam> = arrayOf(
        DetailTeam(0, R.drawable.fortnitelogo,"Team Valorant A", arrayOf("Alice", "Bob", "Charlie", "David", "Eve"), arrayOf("Leader", "Attacker", "Defender", "Strategist", "Sniper")),
        DetailTeam(1, R.drawable.fortnitelogo,"Team Valorant B", arrayOf("Frank", "Grace", "Hank", "Ivy", "Jack"), arrayOf("Support", "Leader", "Sniper", "Defender", "Attacker")),
        DetailTeam(2, R.drawable.fortnitelogo,"Team Valorant C", arrayOf("Kenny", "Liam", "Mia", "Noah", "Olivia"), arrayOf("Strategist", "Support", "Leader", "Sniper", "Attacker")),
        DetailTeam(3, R.drawable.fortnitelogo,"Team Fortnite A", arrayOf("Paul", "Quinn", "Rachel", "Steve"), arrayOf("Scout", "Leader", "Sniper", "Support")),
        DetailTeam(4, R.drawable.fortnitelogo,"Team Fortnite B", arrayOf("Tina", "Uma", "Victor", "Wendy"), arrayOf("Leader", "Support", "Scout", "Sniper")),
        DetailTeam(5, R.drawable.fortnitelogo,"Team Fortnite C", arrayOf("Xander", "Yara", "Zane", "Amy"), arrayOf("Sniper", "Leader", "Attacker", "Support"))
    )
}