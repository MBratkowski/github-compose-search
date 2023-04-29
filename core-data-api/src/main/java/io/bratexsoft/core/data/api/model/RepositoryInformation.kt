package io.bratexsoft.core.data.api.model


data class RepositoryInformation(
    val repositoryId: Int,
    val repositoryName: String,
    val commitInformationList: List<CommitInformation>
)
