package io.bratexsoft.feature.searchRepositories.usecase

import io.bratexsoft.core.data.api.datasource.RepositoriesDataSource
import io.bratexsoft.core.data.api.model.RepositoryInformation
import javax.inject.Inject

class GetSearchedRepositoriesUseCase @Inject constructor(
    private val repositoriesDataSource: RepositoriesDataSource
) {
    suspend operator fun invoke(): List<RepositoryInformation> {
        return repositoriesDataSource.getSearchedRepositoriesAll()
    }
}
