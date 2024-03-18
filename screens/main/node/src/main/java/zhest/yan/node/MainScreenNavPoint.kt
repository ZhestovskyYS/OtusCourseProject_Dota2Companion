package zhest.yan.node

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zhest.yan.core.presentation.navigation.ScreenNavPoint
import zhest.yan.screens.main.presentation.MainScreenHolder

class MainScreenNavPoint(
    private val navigateToPlayerScreen: @Composable (profileId: String) -> Unit
) : ScreenNavPoint {
    override val link = "main_screen"
    override var argument: String? = null


    @ExperimentalLayoutApi
    @ExperimentalMaterial3Api
    @ExperimentalFoundationApi
    override val ui: @Composable (modifier: Modifier) -> Unit = { modifier ->
        MainScreenHolder(modifier, navigateToPlayerScreen)
    }
}
