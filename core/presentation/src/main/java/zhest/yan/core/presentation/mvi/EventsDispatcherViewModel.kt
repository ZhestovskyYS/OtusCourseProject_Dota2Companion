package zhest.yan.core.presentation.mvi

import kotlinx.coroutines.flow.SharedFlow

interface EventsDispatcherViewModel<STATE, EVENT, EFFECT> : UnidirectionalViewModel<STATE, EVENT> {
    val effect: SharedFlow<EFFECT>
}