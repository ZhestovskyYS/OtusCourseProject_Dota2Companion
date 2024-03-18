package zhest.yan.screens.main.presentation

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import zhest.yan.core.presentation.mvi.EventsDispatcherViewModel
import zhest.yan.screens.main.domain.PlayerShortInfo
import zhest.yan.screens.player.domain.PlayerInfo

interface MainScreenContract : EventsDispatcherViewModel<
        MainScreenContract.State,
        MainScreenContract.Event,
        MainScreenContract.Effect
        > {

    data class State(
        val isInitialState: Boolean = true,
        val isLoading: Boolean = false,
        val isPlayerInfoLoading: Boolean = false,
        val isFabVisible: Boolean = false,
        val players: List<PlayerShortInfo> = emptyList(),
        val searchPattern: TextFieldValue = TextFieldValue(""),
        /**
         *  To use with Toast.makeText(context, "message", Toast.Short).show(), SnackBar
         */
        val errorText: String? = null,
    )

    sealed interface Event {
        data class SearchPatternInput(val pattern: TextFieldValue) : Event
        data class PlayerCardWasClicked(val player: PlayerShortInfo) : Event
        data class PlayerCardWasLongClicked(val player: PlayerShortInfo) : Event
        data class PlayerProfileButtonClicked(val url: String): Event
        object RefreshList : Event
        object ListIsOnTop: Event
        object ListWasOverScrolled : Event
        object FabWasClicked : Event
    }

    sealed interface Effect {
        data class ShowPlayerCardDialog(val player: PlayerInfo) : Effect
        data class NavigateToPlayerScreen(val player: PlayerShortInfo) : Effect
        object ScrollListToTheTop : Effect
        class OpenLink(val open: Context.() -> Unit): Effect
    }
}