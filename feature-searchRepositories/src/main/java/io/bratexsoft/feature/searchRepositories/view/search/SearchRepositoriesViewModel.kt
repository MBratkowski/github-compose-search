package io.bratexsoft.feature.searchRepositories.view.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bratexsoft.core.data.api.error.IncorrectPayloadStructureException
import io.bratexsoft.core.data.api.error.RepositoryNotFoundException
import io.bratexsoft.core.data.api.model.CommitInformation
import io.bratexsoft.core.data.api.model.RepositoryInformation
import io.bratexsoft.feature.searchRepositories.usecase.GetRepositoriesUseCase
import io.bratexsoft.feature.searchRepositories.usecase.GetSearchedRepositoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRepositoriesViewModel @Inject constructor(
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val getLastSearchedRepositoriesUseCase: GetSearchedRepositoriesUseCase
) : ViewModel() {

    private val _uiEffect = MutableSharedFlow<SearchRepositoryViewEffect?>()
    val uiEffect: SharedFlow<SearchRepositoryViewEffect?> = _uiEffect

    private val _uiState = MutableStateFlow(SearchRepositoriesState())
    val uiState: StateFlow<SearchRepositoriesState> = _uiState

    fun dispatchEvent(event: SearchRepositoriesViewEvent) {
        when (event) {
            is SearchRepositoriesViewEvent.SearchRepository -> searchRepositories(event.repositoryName)
            is SearchRepositoriesViewEvent.GetSearchedRepositories -> getSearchedRepositories()
            is SearchRepositoriesViewEvent.OpenRepositoryDetails -> showRepositoryDetails(event.repositoryInformation)
            is SearchRepositoriesViewEvent.AddCommitToShare -> addCommitToShare(event)
            is SearchRepositoriesViewEvent.CleanCommitsToShare -> clearCommitsToShare()
            is SearchRepositoriesViewEvent.DismissDialog ->
                viewModelScope.launch { _uiEffect.emit(null) }
        }
    }

    fun provideCommitsListToShare(): List<CommitInformation> {
        val commitsToShare = mutableListOf<CommitInformation>()
        val shaCommitsToShareList = _uiState.value.shaCommitToShareList
        val stateValue = _uiState.value
        if (stateValue.repositoryInformation != null) {
            val currentCommitsList = stateValue.repositoryInformation.commitInformationList
            shaCommitsToShareList.forEach { sha ->
                val commitToShare = currentCommitsList.find { it.sha == sha }
                if (commitToShare != null) {
                    commitsToShare.add(commitToShare)
                }
            }
        }
        return commitsToShare
    }

    private fun getSearchedRepositories() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.updateState { currentState ->
                currentState.copy(
                    searchedRepositories = getLastSearchedRepositoriesUseCase()
                )
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    private fun searchRepositories(repositoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.updateState { currentState ->
                    currentState.copy(
                        isLoading = true
                    )
                }
                val result = getRepositoriesUseCase(repositoryName)
                _uiState.updateState { currentState ->
                    currentState.copy(
                        repositoryInformation = result,
                        isLoading = false
                    )
                }
            } catch (error: Throwable) {
                _uiEffect.emit(error.convertToViewEffect())
                _uiState.updateState { currentState -> currentState.copy(isLoading = false) }
            }
        }
    }

    private fun showRepositoryDetails(repositoryInformation: RepositoryInformation) {
        _uiState.updateState { currentState ->
            currentState.copy(repositoryInformation = repositoryInformation)
        }
    }

    private fun addCommitToShare(event: SearchRepositoriesViewEvent.AddCommitToShare) {
        val currentShaCommitsList = _uiState.value.shaCommitToShareList
        if (currentShaCommitsList.contains(event.commitSha)) {
            currentShaCommitsList.remove(event.commitSha)
        } else {
            currentShaCommitsList.add(event.commitSha)
        }
        _uiState.updateState { currentState ->
            currentState.copy(
                shaCommitToShareList = currentShaCommitsList
            )
        }
    }

    private fun clearCommitsToShare() {
        val currentShaCommitsList = _uiState.value.shaCommitToShareList
        currentShaCommitsList.clear()
        _uiState.updateState { currentState ->
            currentState.copy(
                shaCommitToShareList = currentShaCommitsList
            )
        }
    }
}

fun Throwable.convertToViewEffect(): SearchRepositoryViewEffect {
    return when (this) {
        is IncorrectPayloadStructureException -> {
            SearchRepositoryViewEffect.IncorrectPayloadErrorDialog
        }
        is RepositoryNotFoundException -> SearchRepositoryViewEffect.RepositoryNotFoundErrorDialog
        else -> SearchRepositoryViewEffect.ErrorDialog

    }
}

inline fun <T> MutableStateFlow<T>.updateState(state: (state: T) -> T) {
    this.value = state(this.value)
}
