package io.bratexsoft.feature.searchRepositories.view.search

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.bratexsoft.core.data.api.model.CommitInformation
import io.bratexsoft.core.data.api.model.RepositoryInformation
import io.bratexsoft.core.designsystem.component.*
import io.bratexsoft.feature.searchRepositories.R
import io.bratexsoft.feature.searchRepositories.util.SendCommitsIntentProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import io.bratexsoft.core.designsystem.R.dimen as designSystemDimens


typealias OnClickWithString = (text: String) -> Unit
typealias OnClickWithRepository = (repositoryItem: RepositoryInformation) -> Unit
typealias OnCommitChecked = (isChecked: Boolean, commitSha: String) -> Unit
typealias OnCommitSelection = (isSelectedMode: Boolean) -> Unit


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRepositoriesScreen(
    viewModel: SearchRepositoriesViewModel = hiltViewModel(),
    actionProvider: SearchRepositoriesActionProvider = SearchRepositoriesActionProvider(viewModel),
    sendCommitsIntentProvider: SendCommitsIntentProvider
) {
    val uiEvent = viewModel.uiEvent.collectAsState(initial = null)
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
                    actionLabel = snackBarActionLabel,
                )
                when (snackBarResult) {
                    SnackbarResult.ActionPerformed -> sendCommitsIntentProvider(viewModel.provideCommitsListToShare())
                    else -> {
                        //Do nothing
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = "") {
        viewModel.dispatchEvent(SearchRepositoriesViewEvent.GetSearchedRepositories)
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        content = { innerPadding ->
            Modifier.padding(innerPadding)
            Column(modifier = Modifier.padding(dimensionResource(id = designSystemDimens.spacingLarge))) {
                Search(searchRepository = actionProvider.provideSearchRepositoryAction())
                Content(
                    uiState.value,
                    openRepositoryDetails = actionProvider.provideOpenRepositoryDetailsAction(),
                    onCommitChecked = actionProvider.provideOnCommitCheckedAction {
                        showSnackBar()
                    },
                    onCommitSelection = actionProvider.provideOnCommitSelectionAction { snackBarJob?.cancel() }
                )
            }
        }
    )

    uiEvent.value?.let {
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
    openRepositoryDetails: OnClickWithRepository,
    onCommitChecked: OnCommitChecked,
    onCommitSelection: OnCommitSelection,
) {
    Crossfade(targetState = state.provideStateStatus()) {
        when (it) {
            is StateStatus.Loading -> CenterCircleProgressIndicator(state.isLoading)
            is StateStatus.Content.Repository -> RepositoryInformation(
                repositoryInformation = it.repositoryInformation,
                onCommitChecked = onCommitChecked,
                onCommitSelection = onCommitSelection
            )
            is StateStatus.Content.SearchedRepositories -> SearchedRepositories(
                it.searchedRepositories,
                openRepositoryDetails = openRepositoryDetails
            )
            is StateStatus.Idle -> {

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
            items(
                items = searchedRepositories,
                itemContent = {
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
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun RepositoryInformation(
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
                    modifier = Modifier.padding(8.dp)
                )
            }
            SpacerMedium()
            CommitsList(
                commitsList = repositoryInformation.commitInformationList,
                onCommitChecked = onCommitChecked,
                onCommitSelection = onCommitSelection
            )
        }
    }
}

@Composable
fun CommitsList(
    commitsList: List<CommitInformation>,
    onCommitChecked: OnCommitChecked,
    onCommitSelection: OnCommitSelection
) {
    var selectionMode by remember { mutableStateOf(false) }
    LazyColumn(content = {
        items(
            items = commitsList,
            itemContent = {
                CommitItem(
                    commitInformation = it,
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
    isSelectionMode: Boolean,
    onLongClick: () -> Unit,
    onCheckedChange: (checked: Boolean, commitSha: String) -> Unit
) {
    BaseCard() {
        Row {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = designSystemDimens.spacingMedium))
                    .clip(RoundedCornerShape(dimensionResource(id = designSystemDimens.spacingMedium)))
                    .combinedClickable(
                        onLongClick = onLongClick,
                        onClick = { })
                    .padding(dimensionResource(id = designSystemDimens.spacingMedium))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(
                                id = R.string.search_repositories_author,
                                commitInformation.author
                            ),
                            maxLines = 1
                        )
                        Text(
                            text = stringResource(
                                id = R.string.search_repositories_message,
                                commitInformation.message
                            ),
                            maxLines = 1
                        )
                        Text(
                            text = stringResource(
                                id = R.string.search_repositories_sha,
                                commitInformation.sha.dropLast(15)
                            ),
                            overflow = TextOverflow.Visible,
                            maxLines = 1
                        )
                    }
                    Box(modifier = Modifier.width(48.dp)) {
                        this@Column.AnimatedVisibility(
                            visible = isSelectionMode,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            var isSelected by rememberSaveable { mutableStateOf(false) }
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = {
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
fun SearchButton(onClick: () -> Unit, text: String) {
    val animatedButtonColors = animateColorAsState(
        targetValue = if (text.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(100, 0)
    )
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = animatedButtonColors.value,
            disabledContainerColor = animatedButtonColors.value,
        ),
        onClick = onClick,
        enabled = text.isNotEmpty(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.search_repositories),
        )
    }
}
