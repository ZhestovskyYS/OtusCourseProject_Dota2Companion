package zhest.yan.screens.player.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zhest.yan.core.presentation.R
import zhest.yan.core.widjets.PlayerInfoCard
import zhest.yan.screens.player.domain.PlayerInfo


@Composable
fun PlayerInfoScreenContent(
    modifier: Modifier = Modifier,
    player: PlayerInfo,
    onProfileLinkButtonIsClicked: (url: String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))

            PlayerInfoCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                nickname = player.nickname,
                lastOnline = player.lastOnline,
                hasDotaPlus = player.hasDotaPlus,
                avatarUrl = player.avatar,
                onSteamProfileLinkIsClicked = {
                    onProfileLinkButtonIsClicked(player.steamProfileLink)
                },
                onProfileLinkIsClicked = {
                    onProfileLinkButtonIsClicked(player.profileLink)
                },
            )
        }

        item {
            Spacer(modifier = Modifier.height(14.dp))

            PlayerStatsOutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                wins = player.wins.toString(),
                losses = player.losses.toString(),
                winRate = "%.2f".format(player.winRate),
            )
        }

        item {
            Spacer(modifier = Modifier.height(14.dp))

            PlayerBestHeroOutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                heroImageUrl = player.mostPlayedHeroImageUrl,
                heroName = player.mostPlayedHeroName
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun chooseAvatar(avatar: Bitmap?) =
    if (avatar == null) painterResource(id = R.drawable.dota2_icon_placeholder)
    else BitmapPainter(avatar.asImageBitmap())


@Composable
@Preview(showSystemUi = true)
private fun PlayerInfoScreenContent_Preview() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(horizontal = 14.dp),
        ) {
            PlayerInfoScreenContent(
                player = PlayerInfo(
                    nickname = "Dyrachyo",
                    lastOnline = "12 hours ago",
                    avatar = "null",
                    hasDotaPlus = true,
                    losses = 3160,
                    wins = 3823,
                    winRate = 54.75f,
                    mostPlayedHeroName = "Juggernaut",
                    mostPlayedHeroImageUrl = "null",
                    profileLink = "https://www.opendota.com/players/116934015",
                    steamProfileLink = "https://steamcommunity.com/id/dyrachyoo/",
                ),
                onProfileLinkButtonIsClicked = { }
            )
        }

    }
}