package io.bratexsoft.githubrepository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.bratexsoft.core.data.RepositoriesDataSourceImpl
import io.bratexsoft.core.data.api.datasource.RepositoriesDataSource
import io.bratexsoft.core.data.local.client.datasource.RepositoriesLocalDataSource
import io.bratexsoft.core.network.datasource.RepositoriesNetworkDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesDataSourceModule {

    @Provides
    fun providesRepositoriesDataSource(
        networkDataSource: RepositoriesNetworkDataSource,
        localNetworkDataSource: RepositoriesLocalDataSource
    ): RepositoriesDataSource {
        return RepositoriesDataSourceImpl(
            networkDataSource,
            localNetworkDataSource
        )
    }
}
