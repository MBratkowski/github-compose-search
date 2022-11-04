package io.bratexsoft.feature.searchRepositories.view.search

import io.bratexsoft.core.data.api.model.RepositoryInformation

data class SearchRepositoriesState(
    val isLoading: Boolean = false,
    val shaCommitToShareList: MutableList<String> = mutableListOf(),
    val searchedRepositories: List<RepositoryInformation>? = null,
    val repositoryInformation: RepositoryInformation? = null
) {
    fun provideScreenState(): ScreenState {
        return when {
            isLoading -> ScreenState.Loading
            repositoryInformation != null -> ScreenState.Content.Repository(repositoryInformation)
            searchedRepositories != null -> ScreenState.Content.SearchedRepositories(
                searchedRepositories
            )
            else -> ScreenState.Idle
        }
    }
}

sealed interface ScreenState {
    object Loading : ScreenState
    sealed class Content : ScreenState {
        data class Repository(val repositoryInformation: RepositoryInformation) : Content()
        data class SearchedRepositories(val searchedRepositories: List<RepositoryInformation>) :
            Content()
    }

    object Idle : ScreenState
}