package zhest.yan.screens.player.domain


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import zhest.yan.core.entities.ResultState
import zhest.yan.screens.player.data.dto.PlayerDto
import zhest.yan.screens.player.data.sources.LocalDataSource
import zhest.yan.screens.player.data.sources.RemoteDataSource
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val NONE = "none"
private const val PROFILE_HOST = "https://www.opendota.com/players/"

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) {

    suspend fun getPlayerInfo(playerId: String): ResultState<PlayerInfo> =
        withContext(Dispatchers.IO) {
            try {
                ResultState.Success(
                    getPlayerInfoFromLocal(playerId) ?: getPlayerInfoFromRemote(playerId)
                )
            } catch (e: Exception) {
                ResultState.Error(e)
            }
        }

    //region getPlayerInfoFromLocal
    private suspend fun getPlayerInfoFromLocal(playerId: String) =
        localDataSource
            .getPlayer(playerId)
            ?.mapDtoToInfo()

    private fun PlayerDto.mapDtoToInfo() = PlayerInfo(
        nickname = nickname,
        avatar = avatar,
        lastOnline = lastOnline,
        hasDotaPlus = hasDotaPlus,
        mmr = mmr,
        wins = wins,
        losses = losses,
        winRate = winRate,
        mostPlayedHeroName = mostPlayedHeroName.takeUnless(CharSequence::isBlank),
        mostPlayedHeroImageUrl = mostPlayedHeroImageUrl.takeUnless(CharSequence::isBlank),
        profileLink = profileLink,
        steamProfileLink = steamProfileLink,
    )
    //endregion

    //region getPlayerInfoFromRemote
    private suspend fun getPlayerInfoFromRemote(playerId: String): PlayerInfo = supervisorScope {
        val mostPlayedHero = async {
            localDataSource.getHero(
                remoteDataSource.instance
                    .getPlayerHeroes(playerId)
                    .maxBy { it.games }
                    .id
            )
        }
        val mainPlayerInfo = async {
            remoteDataSource.instance.getPlayerInfo(playerId)
        }
        val playerWinLoseStats = async {
            remoteDataSource.instance.getPlayerWinsLosses(playerId)
        }


        val (mmr, profile) = mainPlayerInfo.await()
        val winLoseStats = playerWinLoseStats.await()
        val hero = mostPlayedHero.await()

        val playerInfo = PlayerInfo(
            nickname = profile.nickname,
            avatar = profile.avatarUrl,
            lastOnline = fetchDate(profile.lastLogin),
            hasDotaPlus = profile.hasDotaPlus,
            mmr = mmr.estimate ?: 0,
            wins = winLoseStats.wins,
            losses = winLoseStats.losses,
            winRate = winLoseStats.winRate,
            mostPlayedHeroName = hero?.name,
            mostPlayedHeroImageUrl = hero?.imageUrl,
            profileLink = buildProfileLink(profile.id),
            steamProfileLink = profile.steamProfileLink,
        )
        launch { saveFetchedPlayer(playerInfo, profile.id) }
        return@supervisorScope playerInfo
    }

    private fun buildProfileLink(profileId: Int) =
        "$PROFILE_HOST$profileId"

    private fun fetchDate(lastLogin: String?): String {
        if (lastLogin == null) return "no date provided"

        // date format : yyyy-MM-dd’T’HH:mm:ss.SSS’Z’
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = formatter.parse(lastLogin)
        return getDateDiff(date)
    }

    private fun getDateDiff(date: Date): String {
        var msDiff = Date().time - date.time

        // 1ms 1000s 60_000m 3_600_000h 86_400_000d
        val days = msDiff / 86_400_000L
        msDiff -= days
        val hours = msDiff / 3_600_000L
        msDiff -= hours
        val mins = msDiff / 60_000L
        msDiff -= mins
        val seconds = msDiff / 1000L

        val leadingMeasure =
            if (days != 0L) "$days days"
            else if (hours != 0L) "$hours hours"
            else if (mins != 0L) "$mins mins"
            else if (seconds != 0L) "$seconds mins"
            else NONE

        return if (leadingMeasure != NONE) "$leadingMeasure ago" else "just now"
    }


    private suspend fun saveFetchedPlayer(playerInfo: PlayerInfo, playerId: Int) = supervisorScope {
        localDataSource.savePlayer(
            id = playerId.toString(),
            createdAt = Date().time,
            player = playerInfo.toPlayerDto()
        )
    }

    private fun PlayerInfo.toPlayerDto() = PlayerDto(
        nickname,
        avatar,
        lastOnline,
        hasDotaPlus,
        mmr,
        wins,
        losses,
        winRate,
        mostPlayedHeroName ?: "",
        mostPlayedHeroImageUrl ?: "",
        profileLink,
        steamProfileLink
    )
    //endregion
}