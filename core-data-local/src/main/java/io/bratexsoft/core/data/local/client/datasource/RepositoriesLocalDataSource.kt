package io.bratexsoft.core.data.local.client.datasource

import io.bratexsoft.core.data.api.model.RepositoryInformation

interface RepositoriesLocalDataSource {

    suspend fun insertRepository(repositoryInformation: RepositoryInformation)

    suspend fun getAllRepositoryInformation(): List<RepositoryInformation>
}