package io.bratexsoft.feature.searchRepositories.view.search

import io.bratexsoft.core.data.api.model.RepositoryInformation

sealed interface SearchRepositoriesViewEvent {
    object GetSearchedRepositories : SearchRepositoriesViewEvent
    data class AddCommitToShare(val isChecked: Boolean, val commitSha: String) :
        SearchRepositoriesViewEvent

    data class SearchRepository(val repositoryName: String) : SearchRepositoriesViewEvent
    data class OpenRepositoryDetails(val repositoryInformation: RepositoryInformation) :
        SearchRepositoriesViewEvent

    object CleanCommitsToShare : SearchRepositoriesViewEvent
    object DismissDialog : SearchRepositoriesViewEvent
}
