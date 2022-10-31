package io.bratexsoft.feature.searchRepositories.view.search

import io.bratexsoft.core.data.api.model.RepositoryInformation

data class SearchRepositoriesState(
    val isLoading: Boolean = false,
    val shaCommitToShareList: MutableList<String> = mutableListOf(),
    val searchedRepositories: List<RepositoryInformation>? = null,
    val repositoryInformation: RepositoryInformation? = null
) {
    fun provideStateStatus(): StateStatus {
        return when {
            isLoading -> StateStatus.Loading
            repositoryInformation != null -> StateStatus.Content.Repository(repositoryInformation)
            searchedRepositories != null -> StateStatus.Content.SearchedRepositories(
                searchedRepositories
            )
            else -> StateStatus.Idle
        }
    }
}

sealed interface StateStatus {
    object Loading : StateStatus
    sealed class Content : StateStatus {
        data class Repository(val repositoryInformation: RepositoryInformation) : Content()
        data class SearchedRepositories(val searchedRepositories: List<RepositoryInformation>) :
            Content()
    }

    object Idle : StateStatus
}