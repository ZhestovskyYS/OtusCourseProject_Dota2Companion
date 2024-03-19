package zhest.yan.screens.player.data.sources

import zhest.yan.core.db.Database
import zhest.yan.core.db.entity.PlayerInfoEntity
import zhest.yan.screens.player.data.dto.MostPlayedHeroDto
import zhest.yan.screens.player.data.dto.PlayerDto
import java.util.Date
import javax.inject.Inject

private const val DATA_IS_FRESH_PERIOD = 1000 * 60 * 60 // 1 hour

class LocalDataSource @Inject constructor(
    private val db: Database
) {
    private val PlayerInfoEntity.isValidStill: Boolean
        get() {
            val now = Date().time
            val dataLifeTime = now - createdAt
            return dataLifeTime < DATA_IS_FRESH_PERIOD
        }

    suspend fun getPlayer(profileId: String): PlayerDto? =
        db.playerInfoDao
            .findById(profileId)
            ?.takeIf { entity -> entity.isValidStill }
            ?.let { entity ->
                PlayerDto(
                    nickname = entity.nickname,
                    avatar = entity.avatarUrl,
                    lastOnline = entity.lastOnline,
                    hasDotaPlus = entity.hasDotaPlus,
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