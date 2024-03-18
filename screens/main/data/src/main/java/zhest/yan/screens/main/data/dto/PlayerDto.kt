package zhest.yan.screens.main.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    @SerialName("account_id")
    val id: String,
    @SerialName("personaname")
    val nickname: String,
    @SerialName("avatarfull")
    val avatar: String?,
)
