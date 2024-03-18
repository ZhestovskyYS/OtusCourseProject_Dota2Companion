package zhest.yan.screens.main.presentation.components

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import zhest.yan.core.widjets.PlayerInfoCard
import zhest.yan.screens.player.domain.PlayerInfo
import zhest.yan.core.presentation.R

@Composable
internal fun PlayerCardDialog(
    modifier: Modifier = Modifier,
    player: PlayerInfo,
    onDismissRequest: () -> Unit,
    onProfileLinkIsClicked: (url: String) -> Unit,
) {

    Dialog(onDismissRequest) {
        PlayerInfoCard(
            modifier = modifier,
            nickname = player.nickname,
            hasDotaPlus = player.hasDotaPlus,
            lastOnline = player.lastOnline,
            avatarUrl = player.avatar,
            onProfileLinkIsClicked = { onProfileLinkIsClicked(player.profileLink) },
            onSteamProfileLinkIsClicked = { onProfileLinkIsClicked(player.steamProfileLink) }
        )
    }
}

@Composable
private fun chooseAvatar(playerAvatar: Bitmap?) =
    if (playerAvatar != null) BitmapPainter(playerAvatar.asImageBitmap())
    else painterResource(id = R.drawable.dota2_icon_placeholder)