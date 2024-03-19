package zhest.yan.screens.main.presentation

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import zhest.yan.core.presentation.R
import zhest.yan.core.presentation.mvi.collectInLaunchedEffect
import zhest.yan.core.presentation.mvi.use
import zhest.yan.screens.main.presentation.components.MainScreenContent
import zhest.yan.screens.main.presentation.components.PlayerCardDialog
import zhest.yan.screens.player.domain.PlayerInfo

@OptIn(ExperimentalMaterialApi::class)
@Composable
@ExperimentalLayoutApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel,
    navigateToPlayerScreen: @Composable (profileId: String) -> Unit,
    @DrawableRes
    placeHolderDrawableRes: Int = R.drawable.dota2_logo_icon,
) {
    val (state, event, effect) = use(viewModel)

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var playerToShow: PlayerInfo? by remember { mutableStateOf(null) }
    var playerIdForInfoScreen: String? by remember { mutableStateOf(null) }

    val scaffoldState = rememberScaffoldState()
    val lazyColumnState = rememberLazyListState()
    val firstVisibleItemIndex by remember {
        derivedStateOf { lazyColumnState.firstVisibleItemIndex }
    }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = {
            event(MainScreenContract.Event.RefreshList)
        }
    )

    if (firstVisibleItemIndex >= 3)
        event(MainScreenContract.Event.ListWasOverScrolled)
    else
        event(MainScreenContract.Event.ListIsOnTop)

    if (state.errorText != null)
        Toast.makeText(context, state.errorText, Toast.LENGTH_SHORT).show()

    playerIdForInfoScreen?.let {
        navigateToPlayerScreen(it)
        playerIdForInfoScreen = null
    }

    effect.collectInLaunchedEffect { incomingEffect ->
        when (incomingEffect) {
            is MainScreenContract.Effect.NavigateToPlayerScreen ->
                playerIdForInfoScreen = incomingEffect.player.id

            is MainScreenContract.Effect.ShowPlayerCardDialog ->
                playerToShow = incomingEffect.player

            MainScreenContract.Effect.ScrollListToTheTop ->
                coroutineScope.launch {
                    lazyColumnState.animateScrollToItem(0)
                }

            is MainScreenContract.Effect.OpenLink -> incomingEffect.run {
                context.open()
            }
        }
    }

    playerToShow?.let {
        PlayerCardDialog(
            modifier = Modifier,
            player = it,
            onDismissRequest = { playerToShow = null },
            onProfileLinkIsClicked = { url ->
                event(MainScreenContract.Event.PlayerProfileButtonClicked(url))
            }
        )
    }

    MainScreenContent(
        modifier = modifier,
        searchFieldValue = state.searchPattern,
        pullRefreshState = pullRefreshState,
        scaffoldState = scaffoldState,
        players = state.players,
        lazyColumnState = lazyColumnState,
        placeHolderDrawableRes = placeHolderDrawableRes,
        isRefreshing = state.isLoading,
        isInitialState = state.isInitialState,
        isFabVisible = state.isFabVisible,
        onFabIsClicked = { event(MainScreenContract.Event.FabWasClicked) },
        onSearchTextIsChanged = { event(MainScreenContract.Event.SearchPatternInput(it)) },
        onCardIsClicked = { event(MainScreenContract.Event.PlayerCardWasClicked(it)) },
        onCardLongClick = { event(MainScreenContract.Event.PlayerCardWasLongClicked(it)) },
    )
}

