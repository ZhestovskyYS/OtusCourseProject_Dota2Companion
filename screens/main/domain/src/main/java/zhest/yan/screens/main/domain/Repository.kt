package zhest.yan.screens.main.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import zhest.yan.core.entities.ResultState
import zhest.yan.screens.main.data.dto.PlayerDto
import zhest.yan.screens.main.data.sources.LocalDataSource
import zhest.yan.screens.main.data.sources.RemoteDataSource
import zhest.yan.screens.player.domain.PlayerInfo
import javax.inject.Inject

private const val REMOTE_SEARCH_TIMEOUT = 60 * 1000L // 1 min
private typealias PlayerRepository = zhest.yan.screens.player.domain.Repository
private typealias PlayerLocalDataSource = zhest.yan.screens.player.data.sources.LocalDataSource
private typealias PlayerRemoteDataSource = zhest.yan.screens.player.data.sources.RemoteDataSource

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    playerLocalDataSource: PlayerLocalDataSource,
    playerRemoteDataSource: PlayerRemoteDataSource,
) {
    private val playerRepository = PlayerRepository(playerLocalDataSource, playerRemoteDataSource)

    suspend fun getPlayerById(playerId: String): ResultState<PlayerInfo> =
        playerRepository.getPlayerInfo(playerId)

    //region searchPlayer
    suspend fun searchPlayer(pattern: String): ResultState<List<PlayerShortInfo>> =
        withContext(Dispatchers.IO) {
            try {
                ResultState.Success(
                    searchInRemote(pattern) ?: searchInLocal(pattern)
                )
            } catch (e: Exception) {
                ResultState.Error(e)
            }
        }

    private suspend fun searchInRemote(pattern: String) = withTimeoutOrNull(REMOTE_SEARCH_TIMEOUT) {
        remoteDataSource.instance
            .searchPlayer(pattern)
            .map(::toPlayer)
    }

    private suspend fun searchInLocal(pattern: String) =
        localDataSource
            .searchPlayer(pattern)
            .map(::toPlayer)
    //endregion

    private fun toPlayer(player: PlayerDto) =
        PlayerShortInfo(player.id, player.nickname, player.avatar)
}