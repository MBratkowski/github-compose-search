package io.bratexsoft.feature.searchRepositories.view.search

sealed interface SearchRepositoryViewEffect {
    object IncorrectPayloadErrorDialog : SearchRepositoryViewEffect
    object RepositoryNotFoundErrorDialog : SearchRepositoryViewEffect
    object ErrorDialog : SearchRepositoryViewEffect
}
