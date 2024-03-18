package zhest.yan.screens.main.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zhest.yan.screens.main.domain.PlayerShortInfo

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
internal fun PlayersList(
    modifier: Modifier = Modifier,
    lazyColumnState: LazyListState,
    players: List<PlayerShortInfo>,
    placeHolderDrawableRes: Int,
    onCardIsClicked: (PlayerShortInfo) -> Unit,
    onCardLongClick: (PlayerShortInfo) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyColumnState,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            horizontal = 12.dp, vertical = 18.dp
        )
    ) {
        items(
            count = players.size,
            key = { index -> players[index].id }
        ) { index ->
            PlayerCard(
                modifier = Modifier
                    .combinedClickable(
                        onClick = { onCardIsClicked(players[index]) },
                        onLongClick = { onCardLongClick(players[index]) }
                    ),
                player = players[index],
                placeHolderDrawableRes = placeHolderDrawableRes
            )

            if (index != players.lastIndex)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
        }
    }
}