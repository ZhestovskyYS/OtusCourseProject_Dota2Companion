package zhest.yan.core.entities

sealed interface ProcessState {
    data object Success : ProcessState
    @JvmInline
    value class Error(val throwable: Throwable): ProcessState
}
