package io.bratexsoft.feature.searchRepositories.view.search

import io.bratexsoft.core.data.api.model.RepositoryInformation

class SearchRepositoriesActionProvider(
    private val viewModel: SearchRepositoriesViewModel
) {

    fun invokeSearchRepositoryAction(): (searchedRepository: String) -> Unit {
        return {
            viewModel.dispatchEvent(
                SearchRepositoriesViewEvent.SearchRepository(
                    it
                )
            )
        }
    }

    fun invokeOpenRepositoryDetailsAction(): (repositoryInformation: RepositoryInformation) -> Unit {
        return {
            viewModel.dispatchEvent(
                SearchRepositoriesViewEvent.OpenRepositoryDetails(
                    it
                )
            )
        }
    }

    fun invokeOnCommitCheckedAction(
        snackBarCallback: () -> Unit
    ): (isChecked: Boolean, commitSha: String) -> Unit {
        return { isChecked, commitSha ->
            viewModel.dispatchEvent(
                SearchRepositoriesViewEvent.AddCommitToShare(
                    isChecked = isChecked,
                    commitSha = commitSha
                )
            )
            snackBarCallback()
        }
    }

    fun invokeOnCommitSelectionAction(
        snackBarCallback: () -> Unit
    ): (isSelectedMode: Boolean) -> Unit {
        return { isSelectedMode ->
            if (!isSelectedMode) {
                viewModel.dispatchEvent(SearchRepositoriesViewEvent.CleanCommitsToShare)
                snackBarCallback()
            }
        }
    }
}