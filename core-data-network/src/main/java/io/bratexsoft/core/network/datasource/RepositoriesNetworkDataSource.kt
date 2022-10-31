package io.bratexsoft.core.network.datasource

import io.bratexsoft.core.data.api.model.OrganizationInformation
import io.bratexsoft.core.data.api.model.CommitsListItem
import io.bratexsoft.core.data.api.model.Repositories

interface RepositoriesNetworkDataSource {

    suspend fun getRepositories(organizationInformation: OrganizationInformation): Repositories

    suspend fun getRepositoryCommits(organizationInformation: OrganizationInformation): List<CommitsListItem>
}