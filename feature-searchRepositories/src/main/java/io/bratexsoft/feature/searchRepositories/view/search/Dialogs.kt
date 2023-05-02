package io.bratexsoft.feature.searchRepositories.view.search

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.bratexsoft.feature.searchRepositories.R

@Composable
fun IncorrectPayloadErrorDialog(
    onClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClick,
        title = {
            Text(text = stringResource(id = R.string.search_repositories_dialog_error_title))
        },
        text = {
            Text(text = stringResource(id = R.string.search_repositories_dialog_error_message))
        },
        confirmButton = {
            Button(
                onClick = onClick
            ) {
                Text(stringResource(id = android.R.string.ok))
            }
        }
    )
}

@Composable
fun ResultNotFoundErrorDialog(
    onClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClick,
        title = {
            Text(text = stringResource(id = R.string.search_repositories_repository_not_found_dialog_error_title))
        },
        text = {
            Text(text = stringResource(id = R.string.search_repositories_repository_not_found_error_message))
        },
        confirmButton = {
            Button(
                onClick = onClick
            ) {
                Text(stringResource(id = android.R.string.ok))
            }
        }
    )
}
