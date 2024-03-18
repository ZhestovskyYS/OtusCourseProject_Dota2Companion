package zhest.yan.screens.main.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import zhest.yan.core.presentation.R
import zhest.yan.screens.main.domain.PlayerShortInfo

@ExperimentalFoundationApi
@ExperimentalLayoutApi
@ExperimentalMaterial3Api
@Composable
internal fun MainScreenContent(
    modifier: Modifier = Modifier,
    searchFieldValue: TextFieldValue,
    pullRefreshState: PullToRefreshState,
    players: List<PlayerShortInfo>,
    lazyColumnState: LazyListState,
    @DrawableRes
    placeHolderDrawableRes: Int,
    isInitialState: Boolean,
    isFabVisible: Boolean,
    onFabIsClicked: () -> Unit,
    onSearchTextIsChanged: (TextFieldValue) -> Unit,
    onCardIsClicked: (PlayerShortInfo) -> Unit,
    onCardLongClick: (PlayerShortInfo) -> Unit,
) {
    Scaffold(
        modifier,
        topBar = {
            AppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                searchFieldValue = searchFieldValue,
                onSearchFieldValueChange = onSearchTextIsChanged
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = isFabVisible,
                enter = fadeIn(initialAlpha = 0.3f),
                exit = fadeOut(),
            ) {
                FloatingActionButton(
                    modifier = Modifier.size(56.dp),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    elevation = FloatingActionButtonDefaults.elevation(),
                    shape = RoundedCornerShape(16.dp),
                    onClick = onFabIsClicked,
                ) {
                    Icon(
                        modifier = Modifier.wrapContentSize(),
                        painter = painterResource(id = R.drawable.baseline_arrow_upward_24),
                        contentDescription = "Scroll to the top"
                    )
                }
            }

        },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
        Surface(
            modifier = Modifier.consumeWindowInsets(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (players.isNotEmpty()) {
                    PlayersList(
                        lazyColumnState = lazyColumnState,
                        players = players,
                        placeHolderDrawableRes = placeHolderDrawableRes,
                        onCardIsClicked = onCardIsClicked,
                        onCardLongClick = onCardLongClick,
                    )
                } else {
                    EmptyListPlaceholder(
                        modifier = Modifier.fillMaxSize(),
                        message = if (isInitialState) "No players found"
                        else "Input player's nick to search"
                    )
                }

                PullToRefreshContainer(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = pullRefreshState,
                )
            }
        }
    }
}

@Composable
@Preview(
    showSystemUi = true,
    device = "id:pixel_6a",
    group = "MainScreenPreview",
    name = "EmptyList"
)
@ExperimentalLayoutApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun MainScreenPreview1() {
    val players = listOf<PlayerShortInfo>()
    val coroutineScope = rememberCoroutineScope()
    val lazyColumnState = rememberLazyListState()

    val firstVisibleItemIndex by remember {
        derivedStateOf { lazyColumnState.firstVisibleItemIndex }
    }

    val textFieldValue = remember {
        mutableStateOf(
            TextFieldValue("")
        )
    }
    var isRefreshing by remember { mutableStateOf(false) }
    var isFabVisible by remember { mutableStateOf(true) }
    val pullRefreshState = rememberPullToRefreshState {
        isRefreshing
    }

    MaterialTheme {
        MainScreenContent(
            modifier = Modifier.fillMaxSize(),
            players = players,
            searchFieldValue = textFieldValue.value,
            pullRefreshState = pullRefreshState,
            lazyColumnState = lazyColumnState,
            placeHolderDrawableRes = R.drawable.dota2_logo_icon,
            isInitialState = true,
            isFabVisible = isFabVisible,
            onFabIsClicked = {
                coroutineScope.launch {
                    lazyColumnState.animateScrollToItem(0)
                    isFabVisible = false
                }
            },
            onSearchTextIsChanged = { textFieldValue.value = it },
            onCardIsClicked = {},
            onCardLongClick = {}
        )
    }

    LaunchedEffect(firstVisibleItemIndex) { isFabVisible = firstVisibleItemIndex >= 3 }
}

@Composable
@Preview(
    showSystemUi = true,
    device = "id:pixel_6a",
    group = "MainScreenPreview",
    name = "NotEmptyList"
)
@ExperimentalLayoutApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun MainScreenPreview2() {
    val players = listOf(
        PlayerShortInfo(
            id = "123",
            nickname = "Kostya",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "1234",
            nickname = "Leha",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "12345",
            nickname = "Yarik",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "123456",
            nickname = "KonstAntin",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "123567",
            nickname = "Alex",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "12358",
            nickname = "Alex",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "123569",
            nickname = "Alex",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "1235691",
            nickname = "Alex",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "1235692",
            nickname = "Alex",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "1235693",
            nickname = "Alex",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "1235694",
            nickname = "Alex",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "1235695",
            nickname = "Alex",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "1235696",
            nickname = "Alex",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "1235697",
            nickname = "Alex",
            avatarUrl = null
        ),
        PlayerShortInfo(
            id = "1235698",
            nickname = "Alex",
            avatarUrl = null
        )
    )
    val coroutineScope = rememberCoroutineScope()
    val lazyColumnState = rememberLazyListState()

    val firstVisibleItemIndex by remember {
        derivedStateOf { lazyColumnState.firstVisibleItemIndex }
    }

    val textFieldValue = remember {
        mutableStateOf(
            TextFieldValue("")
        )
    }
    var isRefreshing by remember { mutableStateOf(false) }
    var isFabVisible by remember { mutableStateOf(true) }
    val pullRefreshState = rememberPullToRefreshState {
        isRefreshing
    }

    MaterialTheme {
        MainScreenContent(
            modifier = Modifier.fillMaxSize(),
            players = players,
            searchFieldValue = textFieldValue.value,
            pullRefreshState = pullRefreshState,
            lazyColumnState = lazyColumnState,
            placeHolderDrawableRes = R.drawable.dota2_logo_icon,
            isInitialState = false,
            isFabVisible = isFabVisible,
            onFabIsClicked = {
                coroutineScope.launch {
                    lazyColumnState.animateScrollToItem(0)
                    isFabVisible = false
                }
            },
            onSearchTextIsChanged = { textFieldValue.value = it },
            onCardIsClicked = {},
            onCardLongClick = {}
        )
    }

    LaunchedEffect(firstVisibleItemIndex) { isFabVisible = firstVisibleItemIndex >= 3 }
}
