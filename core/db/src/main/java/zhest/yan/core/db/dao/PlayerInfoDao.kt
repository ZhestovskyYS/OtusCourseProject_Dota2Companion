package zhest.yan.core.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import zhest.yan.core.db.entity.PlayerInfoEntity

@Dao
interface PlayerInfoDao {

    @Insert
    suspend fun insertAll(list: List<PlayerInfoEntity>)

    @Insert
    suspend fun insert(player: PlayerInfoEntity)

    @Delete
    suspend fun remove(playerInfo: PlayerInfoEntity)

    @Query("""
        SELECT * FROM player_info WHERE
        id LIKE :profileId
        LIMIT 1
    """)
    suspend fun findById(profileId: String): PlayerInfoEntity?

    @Query("""
        SELECT * FROM player_info WHERE
        nickname LIKE :pattern
        ORDER BY nickname DESC
    """)
    suspend fun searchByName(pattern: String): List<PlayerInfoEntity>
}