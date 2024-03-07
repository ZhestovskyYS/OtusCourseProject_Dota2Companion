package zhest.yan.screens.player.data.sources

import zhest.yan.core.db.Database
import zhest.yan.core.db.entity.PlayerInfoEntity
import zhest.yan.screens.player.data.dto.MostPlayedHeroDto
import zhest.yan.screens.player.data.dto.PlayerDto
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val db: Database
) {
    suspend fun getPlayer(profileId: String): PlayerDto? =
        db.playerInfoDao
            .findById(profileId)
            ?.let { entity ->
                PlayerDto(
                    nickname = entity.nickname,
                    avatar = entity.avatarUrl,
                    lastOnline = entity.lastOnline,
                    hasDotaPlus = entity.hasDotaPlus,
                    mmr = entity.mmr,
                    wins = entity.wins,
                    losses = entity.losses,
                    winRate = entity.winRate,
                    mostPlayedHeroName = entity.mostPlayedHeroName,
                    mostPlayedHeroImageUrl = entity.mostPlayedHeroImageUrl,
                    profileLink = entity.profileLink,
                    steamProfileLink = entity.steamProfileLink,
                )
            }

    suspend fun savePlayer(id: String, createdAt: Long, player: PlayerDto) {
        db.playerInfoDao
            .insert(
                PlayerInfoEntity(
                    id = id,
                    createdAt = createdAt,
                    nickname = player.nickname,
                    avatarUrl = player.avatar,
                    lastOnline = player.lastOnline,
                    hasDotaPlus = player.hasDotaPlus,
                    mmr = player.mmr,
                    wins = player.wins,
                    losses = player.losses,
                    winRate = player.winRate,
                    mostPlayedHeroName = player.mostPlayedHeroName,
                    mostPlayedHeroImageUrl = player.mostPlayedHeroImageUrl,
                    profileLink = player.profileLink,
                    steamProfileLink = player.steamProfileLink,
                )
            )
    }

    suspend fun getHero(heroId: Int): MostPlayedHeroDto? =
        db.heroDao
            .finById(heroId)
            ?.let { entity ->
                MostPlayedHeroDto(
                    name = entity.name,
                    imageUrl = entity.imgUrl,
                )
            }
}