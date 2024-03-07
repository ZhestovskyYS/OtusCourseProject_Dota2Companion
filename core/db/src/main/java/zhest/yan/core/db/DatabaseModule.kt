package zhest.yan.core.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zhest.yan.core.db.dao.HeroDao
import javax.inject.Singleton

private const val DB_NAME = "MainAppDatabase"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): RoomDatabase =
        Room
            .databaseBuilder(
                context,
                Database::class.java,
                DB_NAME,
            )
            .build()
}