package zhest.yan.screens.player.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerInfoDto(
    val profile: Profile,
    val rankTier: Int?
)

@Serializable
data class Profile(
    @SerialName("account_id")
    val id: Int,
    @SerialName("personaname")
    val nickname: String,
    @SerialName("avatar")
    val avatarUrl: String,
    @SerialName("plus")
    val hasDotaPlus: Boolean,
    @SerialName("profileurl")
    val steamProfileLink: String,
    @SerialName("last_login")
    val lastLogin: String?,
)