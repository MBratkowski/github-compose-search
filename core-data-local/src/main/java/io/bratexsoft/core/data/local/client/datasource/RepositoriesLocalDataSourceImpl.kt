package io.bratexsoft.core.data.local.client.datasource

import io.bratexsoft.core.data.api.model.CommitInformation
import io.bratexsoft.core.data.api.model.RepositoryInformation
import io.bratexsoft.core.data.local.client.client.RepositoriesDatabaseClient
import io.bratexsoft.core.data.local.client.model.CommitInformationDb
import io.bratexsoft.core.data.local.client.model.RepositoryInformationDb

class RepositoriesLocalDataSourceImpl(
    private val repositoriesDatabaseClient: RepositoriesDatabaseClient
) : RepositoriesLocalDataSource {

    override suspend fun insertRepository(repositoryInformation: RepositoryInformation) {
        repositoriesDatabaseClient
            .repositoriesDao()
            .insertRepository(
                RepositoryInformationDb(
                    id = repositoryInformation.repositoryId,
                    repositoryName = repositoryInformation.repositoryName
                )
            )

        repositoriesDatabaseClient.commitsDao().insertAllCommits(
            repositoryInformation.commitInformationList.map { commit ->
                CommitInformationDb(
                    repositoryId = repositoryInformation.repositoryId,
                    sha = commit.sha,
                    message = commit.message,
                    date = commit.date,
                    author = commit.author
                )
            }
        )
    }

    override suspend fun getAllRepositoryInformation(): List<RepositoryInformation> {
        return repositoriesDatabaseClient
            .repositoriesDao()
            .getRepositoryWithCommits()
            .map { repositoryInformationDb ->
                Pair(
                    repositoryInformationDb,
                    repositoriesDatabaseClient.commitsDao()
                        .getAllCommitsFromRepository(repositoryId = repositoryInformationDb.id)
                )
            }.map { pair ->
                val repository = pair.first
                val commitsList = pair.second
                RepositoryInformation(
                    repositoryId = repository.id,
                    repositoryName = repository.repositoryName,
                    commitInformationList = commitsList.map { commit ->
                        CommitInformation(
                            date = commit.date,
                            message = commit.message,
                            sha = commit.sha,
                            author = commit.author
                        )
                    }
                )
            }
    }
}