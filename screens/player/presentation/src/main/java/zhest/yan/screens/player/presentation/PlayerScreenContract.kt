package zhest.yan.screens.player.presentation

import android.content.Context
import zhest.yan.core.presentation.mvi.EventsDispatcherViewModel
import zhest.yan.screens.player.domain.PlayerInfo

interface PlayerScreenContract : EventsDispatcherViewModel<
        PlayerScreenContract.State,
        PlayerScreenContract.Event,
        PlayerScreenContract.Effect
        > {

    data class State(
        val playerInfo: PlayerInfo? = null,
        val isLoading: Boolean = true,
        val errorText: String? = null
    )

    sealed interface Event {
        data class PlayerProfileButtonWasClicked(val url: String): Event
    }

    sealed interface Effect {
        class OpenLink(val open: Context.() -> Unit): Effect
    }
}