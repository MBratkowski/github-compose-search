package io.bratexsoft.core.data

import io.bratexsoft.core.data.api.datasource.RepositoriesDataSource
import io.bratexsoft.core.data.api.model.OrganizationInformation
import io.bratexsoft.core.data.api.model.RepositoryInformation
import io.bratexsoft.core.data.local.client.datasource.RepositoriesLocalDataSource
import io.bratexsoft.core.network.datasource.RepositoriesNetworkDataSource
import javax.inject.Inject

class RepositoriesDataSourceImpl @Inject constructor(
    private val repositoriesNetworkDataSource: RepositoriesNetworkDataSource,
    private val repositoriesLocalDataSource: RepositoriesLocalDataSource
) : RepositoriesDataSource {

    override suspend fun getRepositoryInfo(organizationInformation: OrganizationInformation): RepositoryInformation {
        val repositories = repositoriesNetworkDataSource.getRepositories(organizationInformation)
        // FIXME val commits = repositoriesNetworkDataSource.getRepositoryCommits(organizationInformation)

        val repositoryInformation = RepositoryInformation(
            repositoryName = repositories.name.value,
            repositoryId = repositories.id.value,
            commitInformationList = emptyList()
        )

        repositoriesLocalDataSource.insertRepository(repositoryInformation)

        return repositoryInformation
    }

    override suspend fun getSearchedRepositoriesAll(): List<RepositoryInformation> {
        return repositoriesLocalDataSource.getAllRepositoryInformation()
    }
}
