package io.bratexsoft.core.data

import io.bratexsoft.core.data.api.datasource.RepositoriesDataSource
import io.bratexsoft.core.data.api.model.CommitInformation
import io.bratexsoft.core.data.api.model.OrganizationInformation
import io.bratexsoft.core.data.api.model.RepositoryInformation
import io.bratexsoft.core.data.local.client.datasource.RepositoriesLocalDataSource
import io.bratexsoft.core.network.datasource.RepositoriesNetworkDataSource
import javax.inject.Inject

class RepositoriesDataSourceImpl @Inject constructor(
    private val repositoriesNetworkDataSource: RepositoriesNetworkDataSource,
    private val repositoriesLocalDataSource: RepositoriesLocalDataSource,
) : RepositoriesDataSource {

    override suspend fun getRepositoryInfo(organizationInformation: OrganizationInformation): RepositoryInformation {
        val repositories = repositoriesNetworkDataSource.getRepositories(organizationInformation)
        val commits = repositoriesNetworkDataSource.getRepositoryCommits(organizationInformation)

        val repositoryInformation = RepositoryInformation(
            repositoryName = repositories.name,
            repositoryId = repositories.id,
            commitInformationList = commits.map { item ->
                CommitInformation(
                    date = item.commit.author.date,
                    message = item.commit.message,
                    sha = item.sha,
                    author = item.author.login
                )
            }.sortedByDescending { it.date })

        repositoriesLocalDataSource.insertRepository(repositoryInformation)

        return repositoryInformation
    }

    override suspend fun getSearchedRepositoriesAll(): List<RepositoryInformation> {
        return repositoriesLocalDataSource.getAllRepositoryInformation()
    }
}
