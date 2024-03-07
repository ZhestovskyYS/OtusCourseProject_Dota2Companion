package zhest.yan.core.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_info")
data class PlayerInfoEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "nickname")
    val nickname: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "last_online")
    val lastOnline: String,
    @ColumnInfo(name = "has_dota_plus")
    val hasDotaPlus: Boolean,
    @ColumnInfo(name = "mmr")
    val mmr: Int,
    @ColumnInfo(name = "wins")
    val wins: Int,
    @ColumnInfo(name = "losses")
    val losses: Int,
    @ColumnInfo(name = "win_rate")
    val winRate: Float,
    @ColumnInfo(name = "most_played_hero_name")
    val mostPlayedHeroName: String,
    @ColumnInfo(name = "most_played_hero_image_url")
    val mostPlayedHeroImageUrl: String,
    @ColumnInfo(name = "profile_link")
    val profileLink: String,
    @ColumnInfo(name = "steam_profile_link")
    val steamProfileLink: String,
)