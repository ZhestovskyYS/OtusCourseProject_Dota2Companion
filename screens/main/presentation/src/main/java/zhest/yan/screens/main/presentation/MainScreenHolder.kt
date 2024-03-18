package zhest.yan.screens.main.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
@ExperimentalLayoutApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
fun MainScreenHolder(
    modifier: Modifier = Modifier,
    navigateToPlayerScreen: @Composable (profileId: String) -> Unit,
) {
    MainScreen(
        modifier,
        navigateToPlayerScreen = navigateToPlayerScreen,
        viewModel = hiltViewModel<MainScreenViewModel>()
    )
}