package zhest.yan.screens.player.presentation

import android.util.Log
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
import zhest.yan.screens.player.presentation.PlayerScreenContract.*

private const val TAG = "PlayerInfoScreenViewModel"

@HiltViewModel(
    assistedFactory = ViewModelFactory::class
)
class PlayerInfoScreenViewModel @AssistedInject constructor(
    @Assisted private val playerId: String,
    private val repository: Repository,
) : ViewModel(), PlayerScreenContract {
    private val openLinkUseCase: OpenLinkUseCase = OpenLinkUseCase()

    private val _state = MutableStateFlow(State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    override fun event(event: Event) {
        when (event) {
            is Event.PlayerProfileButtonWasClicked -> viewModelScope.launch {
                _effect.emit(
                    Effect.OpenLink {
                        openLinkUseCase.run {
                            invoke(event.url)
                        }
                    }
                )
            }
        }
    }

    private fun fetchPlayerInfo() {
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val response = repository.getPlayerInfo(playerId)) {
                is ResultState.Error -> _state.update {
                    Log.w(
                        TAG,
                        "Error on fetching player info: [${response.throwable.message ?: "Unknown"}]"
                    )
                    _effect.emit(
                        Effect.DisplayErrorMessage(response.throwable.localizedMessage)
                    )
                    it.copy(isLoading = false)
                }

                is ResultState.Success -> _state.update {
                    it.copy(
                        playerInfo = response.result,
                        isLoading = false
                    )
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