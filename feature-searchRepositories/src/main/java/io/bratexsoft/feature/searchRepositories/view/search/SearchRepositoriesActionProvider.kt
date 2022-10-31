package io.bratexsoft.feature.searchRepositories.view.search

import io.bratexsoft.core.data.api.model.RepositoryInformation

class SearchRepositoriesActionProvider(
    private val viewModel: SearchRepositoriesViewModel
) {

    fun provideSearchRepositoryAction(): (searchedRepository: String) -> Unit {
        return {
            viewModel.dispatchEvent(
                SearchRepositoriesViewEvent.SearchRepository(
                    it
                )
            )
        }
    }

    fun provideOpenRepositoryDetailsAction(): (repositoryInformation: RepositoryInformation) -> Unit {
        return {
            viewModel.dispatchEvent(
                SearchRepositoriesViewEvent.OpenRepositoryDetails(
                    it
                )
            )
        }
    }

    fun provideOnCommitCheckedAction(snackBarCallback: () -> Unit): (isChecked: Boolean, commitSha: String) -> Unit {
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

    fun provideOnCommitSelectionAction(snackBarCallback: () -> Unit): (isSelectedMode: Boolean) -> Unit {
        return { isSelectedMode ->
            if (!isSelectedMode) {
                viewModel.dispatchEvent(SearchRepositoriesViewEvent.CleanCommitsToShare)
                snackBarCallback()
            }
        }
    }
}