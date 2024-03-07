package zhest.yan.screens.player.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zhest.yan.core.entities.ResultState
import zhest.yan.screens.player.data.sources.LocalDataSource
import zhest.yan.screens.player.data.sources.RemoteDataSource
import java.util.Date

class Repository(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource,
) {

    suspend fun getPlayerInfo(playerId: String): ResultState<PlayerInfo> =
        withContext(Dispatchers.IO) {
            try {

                ResultState.Success(
                    PlayerInfo(

                    )
                )
            } catch (e: Exception) {
                ResultState.Error(e)
            }
        }

    private suspend fun getPlayerFromLocal(playerId: String): PlayerInfo? =
        localDataSource.getPlayer(playerId)
            ?.takeIf {
                val now = Date().time
                now - it.c
            }


}