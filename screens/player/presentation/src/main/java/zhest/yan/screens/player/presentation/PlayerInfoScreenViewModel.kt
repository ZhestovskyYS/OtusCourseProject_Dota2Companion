package zhest.yan.screens.player.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import zhest.yan.core.entities.ResultState
import zhest.yan.core.presentation.OpenLinkUseCase
import zhest.yan.screens.player.domain.Repository

@HiltViewModel
class PlayerInfoScreenViewModel @AssistedInject constructor(
    @Assisted private val playerId: String,
    private val repository: Repository,
    private val openLinkUseCase: OpenLinkUseCase = OpenLinkUseCase(),
) : ViewModel(), PlayerScreenContract {

    private val _state = MutableStateFlow(PlayerScreenContract.State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PlayerScreenContract.Effect>()
    override val effect = _effect.asSharedFlow()

    override fun event(event: PlayerScreenContract.Event) {
        when (event) {
            is PlayerScreenContract.Event.PlayerProfileButtonWasClicked -> viewModelScope.launch {
                _effect.emit(
                    PlayerScreenContract.Effect.OpenLink {
                        openLinkUseCase.run {
                            invoke(event.url)
                        }
                    }
                )
            }
        }
    }

    private fun fetchPlayerInfo() {
        viewModelScope.launch {
            when (val response = repository.getPlayerInfo(playerId)) {
                is ResultState.Error -> _state.update {
                    it.copy(errorText = response.throwable.localizedMessage)
                }

                is ResultState.Success -> _state.update {
                    it.copy(playerInfo = response.result)
                }
            }
        }
    }

    init {
        fetchPlayerInfo()
    }
}

@AssistedFactory
interface ViewModelFactory {
    fun create(playerId: String): PlayerInfoScreenViewModel
}