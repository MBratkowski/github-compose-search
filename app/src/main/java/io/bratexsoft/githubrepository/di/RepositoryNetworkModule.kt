package io.bratexsoft.githubrepository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.bratexsoft.core.network.api.RepositoriesServiceApi
import io.bratexsoft.core.network.client.RetrofitClientProvider
import io.bratexsoft.core.network.datasource.RepositoriesNetworkDataSource
import io.bratexsoft.core.network.datasource.RepositoriesNetworkDataSourceImpl
import io.bratexsoft.core.network.mapper.RepositoriesMapper

@Module
@InstallIn(SingletonComponent::class)
object RepositoryNetworkModule {

    @Provides
    fun provideRepositoriesServiceApi(): RepositoriesServiceApi {
        return RetrofitClientProvider.provideClient(
            host = "https://api.github.com/",
            api = RepositoriesServiceApi::class.java
        )
    }

    @Provides
    fun provideRepositoriesNetworkDataSource(
        repositoriesServiceApi: RepositoriesServiceApi,
        repositoriesMapper: RepositoriesMapper
    ): RepositoriesNetworkDataSource {
        return RepositoriesNetworkDataSourceImpl(
            repositoriesServiceApi = repositoriesServiceApi,
            repositoriesMapper = repositoriesMapper
        )
    }
}
