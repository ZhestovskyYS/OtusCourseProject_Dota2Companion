package zhest.yan.screens.player.domain

data class PlayerInfo(
    val nickname: String,
    val avatar: String,
    val lastOnline: String,
    val hasDotaPlus: Boolean,
    val mmr: Int,
    val wins: Int,
    val losses: Int,
    val winRate: Float,
    val mostPlayedHeroName: String?,
    val mostPlayedHeroImageUrl: String?,
    val profileLink: String,
    val steamProfileLink: String,
)