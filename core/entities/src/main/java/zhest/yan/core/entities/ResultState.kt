package zhest.yan.core.entities

sealed class ResultState<out T : Any> {
    data class Error(val throwable: Throwable) : ResultState<Nothing>()

    data class Success<out T : Any>(val result: T) : ResultState<T>()
}
