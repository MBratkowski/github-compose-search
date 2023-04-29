package io.bratexsoft.feature.searchRepositories.view.search

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.bratexsoft.core.data.api.model.CommitInformation
import io.bratexsoft.core.data.api.model.RepositoryInformation
import io.bratexsoft.core.designsystem.component.BaseCard
import io.bratexsoft.core.designsystem.component.FadeInFadeOutAnimation
import io.bratexsoft.core.designsystem.component.HeadlineMedium
import io.bratexsoft.core.designsystem.component.HeadlineSmall
import io.bratexsoft.core.designsystem.component.OneLineText
import io.bratexsoft.core.designsystem.component.SpacerMedium
import io.bratexsoft.core.designsystem.component.SpacerSmall
import io.bratexsoft.feature.searchRepositories.R
import io.bratexsoft.feature.searchRepositories.util.SendCommitsIntentProvider
import io.bratexsoft.feature.searchRepositories.util.TextContentProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import io.bratexsoft.core.designsystem.R.dimen as designSystemDimens

typealias OnClickWithString = (text: String) -> Unit
typealias OnClickWithRepository = (repositoryItem: RepositoryInformation) -> Unit
typealias OnCommitChecked = (isChecked: Boolean, commitSha: String) -> Unit
typealias OnCommitSelection = (isSelectedMode: Boolean) -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRepositoriesScreen(
    viewModel: SearchRepositoriesViewModel = hiltViewModel(),
    actionProvider: SearchRepositoriesActionProvider = SearchRepositoriesActionProvider(viewModel),
    sendCommitsIntentProvider: SendCommitsIntentProvider,
    textContentProvider: TextContentProvider
) {
    val uiEffect = viewModel.uiEffect.collectAsState(initial = null)
    val uiState = viewModel.uiState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val snackBarActionLabel = stringResource(id = R.string.search_repositories_snack_bar_action)
    val snackBarMessageTemplate = stringResource(id = R.string.search_repositories_snack_bar_title)

    var snackBarJob: Job? = null

    fun showSnackBar() {
        snackBarJob?.cancel()
        val commitsCount = uiState.value.shaCommitToShareList.size
        scope.launch {
            snackBarJob = scope.launch {
                val snackBarResult: SnackbarResult = snackBarHostState.showSnackbar(
                    message = snackBarMessageTemplate.format(commitsCount),
                    actionLabel = snackBarActionLabel
                )
                when (snackBarResult) {
                    SnackbarResult.ActionPerformed -> sendCommitsIntentProvider(viewModel.provideCommitsListToShare())
                    else -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.dispatchEvent(SearchRepositoriesViewEvent.GetSearchedRepositories)
    }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }, content = { innerPadding ->
            Modifier.padding(innerPadding)
            Column(modifier = Modifier.padding(dimensionResource(id = designSystemDimens.spacingLarge))) {
                Search(searchRepository = actionProvider.invokeSearchRepositoryAction())
                Content(
                    uiState.value,
                    textContentProvider = textContentProvider,
                    openRepositoryDetails = actionProvider.invokeOpenRepositoryDetailsAction(),
                    onCommitChecked = actionProvider.invokeOnCommitCheckedAction { showSnackBar() },
                    onCommitSelection = actionProvider.invokeOnCommitSelectionAction { snackBarJob?.cancel() }
                )
            }
        })

    uiEffect.value?.let {
        when (it) {
            is SearchRepositoryViewEffect.IncorrectPayloadErrorDialog -> {
                IncorrectPayloadErrorDialog {
                    viewModel.dispatchEvent(
                        SearchRepositoriesViewEvent.DismissDialog
                    )
                }
            }

            is SearchRepositoryViewEffect.RepositoryNotFoundErrorDialog -> {
                ResultNotFoundErrorDialog {
                    viewModel.dispatchEvent(
                        SearchRepositoriesViewEvent.DismissDialog
                    )
                }
            }

            else -> {
                // Do nothing
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Search(searchRepository: OnClickWithString) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchedRepositoryTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    HeadlineMedium(text = stringResource(id = R.string.search_repositories))
    SpacerMedium()
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchedRepositoryTextFieldValue,
        onValueChange = { searchedRepositoryTextFieldValue = it },
        maxLines = 1,
        label = { Text(stringResource(id = R.string.search_repositories_hint)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        )
    )
    SpacerMedium()
    SearchButton(
        onClick = {
            searchRepository(searchedRepositoryTextFieldValue.text)
            keyboardController?.hide()
        },
        text = searchedRepositoryTextFieldValue.text
    )
}

@Composable
fun Content(
    state: SearchRepositoriesState,
    textContentProvider: TextContentProvider,
    openRepositoryDetails: OnClickWithRepository,
    onCommitChecked: OnCommitChecked,
    onCommitSelection: OnCommitSelection
) {
    Crossfade(targetState = state.provideScreenState()) {
        when (it) {
            is ScreenState.Loading -> CenterCircleProgressIndicator(state.isLoading)
            is ScreenState.Content.Repository -> RepositoryInformation(
                repositoryInformation = it.repositoryInformation,
                textContentProvider = textContentProvider,
                onCommitChecked = onCommitChecked,
                onCommitSelection = onCommitSelection
            )

            is ScreenState.Content.SearchedRepositories -> SearchedRepositories(
                it.searchedRepositories,
                openRepositoryDetails = openRepositoryDetails
            )

            is ScreenState.Idle -> {
                // Do nothing
            }
        }
    }
}

@Composable
fun SearchedRepositories(
    searchedRepositories: List<RepositoryInformation>,
    openRepositoryDetails: OnClickWithRepository
) {
    Column {
        SpacerMedium()
        HeadlineSmall(text = stringResource(id = R.string.search_repositories_latest))
        SpacerSmall()
        LazyColumn(content = {
            items(items = searchedRepositories, itemContent = {
                RepositoryItem(
                    repositoryInformation = it,
                    openRepositoryDetails = openRepositoryDetails
                )
            })
        })
    }
}

@Composable
fun RepositoryItem(
    repositoryInformation: RepositoryInformation,
    openRepositoryDetails: OnClickWithRepository
) {
    BaseCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { openRepositoryDetails(repositoryInformation) }
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = designSystemDimens.spacingMedium))) {
            Text(
                text = stringResource(
                    id = R.string.search_repositories_repository_name,
                    repositoryInformation.repositoryName
                )
            )
            Text(
                text = stringResource(
                    id = R.string.search_repositories_repository_id,
                    repositoryInformation.repositoryId.toString()
                )
            )
        }
    }
}

@Composable
fun CenterCircleProgressIndicator(isVisible: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FadeInFadeOutAnimation(
            visibility = isVisible
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun RepositoryInformation(
    textContentProvider: TextContentProvider,
    repositoryInformation: RepositoryInformation?,
    onCommitChecked: OnCommitChecked,
    onCommitSelection: OnCommitSelection
) {
    Column {
        repositoryInformation?.let { repositoryInformation ->
            SpacerMedium()
            BaseCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(
                        id = R.string.search_repositories_repository_id,
                        repositoryInformation.repositoryId
                    ),
                    modifier = Modifier.padding(dimensionResource(id = designSystemDimens.spacingMedium))
                )
            }
            SpacerMedium()
            CommitsList(
                commitsList = repositoryInformation.commitInformationList,
                textContentProvider = textContentProvider,
                onCommitChecked = onCommitChecked,
                onCommitSelection = onCommitSelection
            )
        }
    }
}

@Composable
fun CommitsList(
    commitsList: List<CommitInformation>,
    textContentProvider: TextContentProvider,
    onCommitChecked: OnCommitChecked,
    onCommitSelection: OnCommitSelection
) {
    var selectionMode by remember { mutableStateOf(false) }
    LazyColumn(content = {
        items(items = commitsList, itemContent = {
            CommitItem(
                commitInformation = it,
                textContentProvider = textContentProvider,
                selectionMode,
                onLongClick = {
                    selectionMode = !selectionMode
                    onCommitSelection(selectionMode)
                },
                onCheckedChange = onCommitChecked
            )
        })
    })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommitItem(
    commitInformation: CommitInformation,
    textContentProvider: TextContentProvider,
    isSelectionMode: Boolean,
    onLongClick: () -> Unit,
    onCheckedChange: OnCommitChecked
) {
    BaseCard() {
        Row {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = designSystemDimens.spacingMedium))
                    .clip(RoundedCornerShape(dimensionResource(id = designSystemDimens.spacingMedium)))
                    .combinedClickable(onLongClick = onLongClick, onClick = { })
                    .padding(dimensionResource(id = designSystemDimens.spacingMedium))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        OneLineText(text = textContentProvider.provideAuthor(commitInformation.author))
                        OneLineText(text = commitInformation.message)
                        OneLineText(text = commitInformation.sha.dropLast(15))
                    }
                    Box(modifier = Modifier.width(48.dp)) {
                        FadeInFadeOutAnimation(visibility = isSelectionMode) {
                            var isSelected by rememberSaveable { mutableStateOf(false) }
                            Checkbox(checked = isSelected, onCheckedChange = {
                                isSelected = it
                                onCheckedChange(it, commitInformation.sha)
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    val animatedButtonColors = animateColorAsState(
        targetValue = if (text.isNotEmpty()) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        animationSpec = tween(100, 0)
    )
    Button(
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = animatedButtonColors.value,
            disabledContainerColor = animatedButtonColors.value
        ),
        onClick = onClick,
        enabled = text.isNotEmpty()
    ) {
        Text(
            modifier = modifier,
            text = stringResource(id = R.string.search_repositories)
        )
    }
}
