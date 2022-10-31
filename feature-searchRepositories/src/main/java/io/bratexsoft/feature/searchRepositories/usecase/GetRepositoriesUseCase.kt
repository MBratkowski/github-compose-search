package io.bratexsoft.feature.searchRepositories.usecase

import io.bratexsoft.core.data.api.datasource.RepositoriesDataSource
import io.bratexsoft.core.data.api.error.IncorrectPayloadStructureException
import io.bratexsoft.core.data.api.model.OrganizationInformation
import io.bratexsoft.core.data.api.model.RepositoryInformation
import javax.inject.Inject

class GetRepositoriesUseCase @Inject constructor(
    private val repositoriesDataSource: RepositoriesDataSource
) {
    suspend operator fun invoke(payload: String): RepositoryInformation {
        val data = payload.split("/")
        if (data.size == 2) {
            return repositoriesDataSource.getRepositoryInfo(
                OrganizationInformation(
                    ownerName = data.first(),
                    repositoryName = data.last()
                )
            )
        } else {
            throw IncorrectPayloadStructureException()
        }
    }
}