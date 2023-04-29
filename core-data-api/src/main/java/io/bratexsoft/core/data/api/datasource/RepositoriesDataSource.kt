package io.bratexsoft.core.data.api.datasource

import io.bratexsoft.core.data.api.model.OrganizationInformation
import io.bratexsoft.core.data.api.model.RepositoryInformation

interface RepositoriesDataSource {
    suspend fun getRepositoryInfo(organizationInformation: OrganizationInformation): RepositoryInformation

    suspend fun getSearchedRepositoriesAll(): List<RepositoryInformation>
}
