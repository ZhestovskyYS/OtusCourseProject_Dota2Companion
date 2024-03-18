package zhest.yan.screens.player.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zhest.yan.core.presentation.navigation.ScreenNavPoint
import zhest.yan.screens.player.presentation.PlayerInfoScreenHolder

object PlayerScreenNavPoint: ScreenNavPoint {
    override val link: String = "player_screen"
    override var argument: String? = null

    override val ui: @Composable (modifier: Modifier) -> Unit = { modifier ->
        PlayerInfoScreenHolder(modifier, profileId = argument)
    }
}