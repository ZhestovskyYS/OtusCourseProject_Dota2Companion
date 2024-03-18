package zhest.yan.screens.main.data.sources

import zhest.yan.core.db.Database
import zhest.yan.core.db.entity.PlayerInfoEntity
import zhest.yan.screens.main.data.dto.PlayerDto
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

    suspend fun searchPlayer(searchPattern: String): List<PlayerDto> =
        db.playerInfoDao.searchByName(searchPattern).asSequence()
            .filter { it.isValidStill }
            .map {
                PlayerDto(
                    id = it.id,
                    nickname = it.nickname,
                    avatar = it.avatarUrl
                )
            }
            .toList()
}