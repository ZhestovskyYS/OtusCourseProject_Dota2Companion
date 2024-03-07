package zhest.yan.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import zhest.yan.core.db.dao.HeroDao
import zhest.yan.core.db.dao.PlayerInfoDao
import zhest.yan.core.db.entity.HeroEntity
import zhest.yan.core.db.entity.PlayerInfoEntity

@Database(
    entities = [
        HeroEntity::class,
        PlayerInfoEntity::class,
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract val heroDao: HeroDao
    abstract val playerInfoDao: PlayerInfoDao
}