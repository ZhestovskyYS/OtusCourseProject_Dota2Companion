package zhest.yan.core.net

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

private const val ENDPOINT = "https://api.opendota.com/api"

@Module
@InstallIn(SingletonComponent::class)
class NetworkClientModule {
    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    private val jsonSerialization = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    private val jsonConverterFactory = jsonSerialization
        .asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun provideClient(): Retrofit = Retrofit.Builder()
        .client(okHttp)
        .addConverterFactory(jsonConverterFactory)
        .baseUrl(ENDPOINT)
        .build()
}