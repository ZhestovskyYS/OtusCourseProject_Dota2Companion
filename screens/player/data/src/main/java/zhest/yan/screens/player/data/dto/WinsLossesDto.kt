package zhest.yan.screens.player.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WinsLossesDto(
    @SerialName("win")
    val wins: Int,
    @SerialName("lose")
    val losses: Int,
) {
    val winRate: Float = wins.toFloat() / (wins + losses)
}
