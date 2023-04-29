package io.bratexsoft.githubrepository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.bratexsoft.core.network.api.RepositoriesServiceApi
import io.bratexsoft.core.network.client.RetrofitClientProvider
import io.bratexsoft.core.network.datasource.RepositoriesNetworkDataSource
import io.bratexsoft.core.network.datasource.RepositoriesNetworkDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object RepositoryNetworkModule {

    @Provides
    fun provideRepositoriesServiceApi(): RepositoriesServiceApi {
        return RetrofitClientProvider.provideClient(
            "https://api.github.com/",
            RepositoriesServiceApi::class.java
        )
    }

    @Provides
    fun provideRepositoriesNetworkDataSource(
        repositoriesServiceApi: RepositoriesServiceApi
    ): RepositoriesNetworkDataSource {
        return RepositoriesNetworkDataSourceImpl(repositoriesServiceApi)
    }
}
