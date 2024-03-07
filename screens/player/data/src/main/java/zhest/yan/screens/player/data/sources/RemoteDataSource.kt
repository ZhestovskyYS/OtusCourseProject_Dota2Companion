package zhest.yan.screens.player.data.sources

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import zhest.yan.screens.player.data.dto.HeroDto
import zhest.yan.screens.player.data.dto.PlayerInfoDto
import zhest.yan.screens.player.data.dto.WinsLossesDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(retrofit: Retrofit) {

    val instance = retrofit.create(Api::class.java)

    interface Api {
        @GET("/players/{id}")
        suspend fun getPlayerInfo(@Path("id") playerId: String): PlayerInfoDto

        @GET("/players/{id}/heroes")
        suspend fun getPlayerHeroes(@Path("id") playerId: String): List<HeroDto>

        @GET("/players/{id}/wl")
        suspend fun getPlayerWinsLosses(@Path("id") playerId: String): WinsLossesDto
    }
}