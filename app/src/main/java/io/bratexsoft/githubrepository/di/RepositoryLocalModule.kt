package io.bratexsoft.githubrepository.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.bratexsoft.core.data.local.client.client.RepositoriesDatabaseClient
import io.bratexsoft.core.data.local.client.datasource.RepositoriesLocalDataSource
import io.bratexsoft.core.data.local.client.datasource.RepositoriesLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryLocalModule {

    @Provides
    @Singleton
    fun provideRepositoryDatabase(
        @ApplicationContext context: Context
    ): RepositoriesDatabaseClient {
        return Room.databaseBuilder(
            context,
            RepositoriesDatabaseClient::class.java,
            "repositories-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepositoriesLocalDataSource(
        repositoriesDatabaseClient: RepositoriesDatabaseClient
    ): RepositoriesLocalDataSource {
        return RepositoriesLocalDataSourceImpl(repositoriesDatabaseClient)
    }
}