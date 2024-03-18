package zhest.yan.screens.player.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import zhest.yan.screens.player.presentation.components.ErrorPlaceholder

@Composable
fun PlayerInfoScreenHolder(
    modifier: Modifier = Modifier,
    profileId: String?
) {

    Surface(modifier) {
        if (profileId == null) {
            ErrorPlaceholder(
                modifier = Modifier.fillMaxSize(),
                message = "Error on getting profile id!"
            )
        } else {
            PlayerInfoScreen(
                viewModel = hiltViewModel<PlayerInfoScreenViewModel, ViewModelFactory>(
                    key = profileId
                ) { factory ->
                    factory.create(profileId)
                }
            )
        }
    }
}