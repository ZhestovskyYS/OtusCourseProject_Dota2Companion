package zhest.yan.screens.player.presentation

import android.content.Context
import zhest.yan.core.presentation.mvi.EventsDispatcherViewModel
import zhest.yan.screens.player.domain.PlayerInfo

interface PlayerScreenContract :
    EventsDispatcherViewModel<
            PlayerScreenContract.State,
            PlayerScreenContract.Event,
            PlayerScreenContract.Effect
            > {

    data class State(
        val playerInfo: PlayerInfo? = null,
        val isLoading: Boolean = true,
    )

    sealed interface Event {
        data class PlayerProfileButtonWasClicked(val url: String) : Event
    }

    sealed interface Effect {
        fun interface OpenLink : Effect {
            fun Context.open()
        }

        class DisplayErrorMessage(val errorMessage: String) : Effect
    }
}