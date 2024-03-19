package zhest.yan.screens.main.data.sources

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import zhest.yan.screens.main.data.dto.PlayerDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(retrofit: Retrofit) {

    val instance: Api = retrofit.create(Api::class.java)

    interface Api {
        @GET("api/search")
        suspend fun searchPlayer(
            @Query("q") pattern: String
        ): List<PlayerDto>
    }
}