package io.bratexsoft.githubrepository

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.bratexsoft.core.designsystem.theme.GithubRepositoryTheme
import io.bratexsoft.feature.searchRepositories.util.SendCommitsIntentProvider
import io.bratexsoft.feature.searchRepositories.util.TextContentProvider
import io.bratexsoft.feature.searchRepositories.view.search.SearchRepositoriesScreen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sendCommitsIntentProvider: SendCommitsIntentProvider

    @Inject
    lateinit var textContentProvider: TextContentProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubRepositoryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchRepositoriesScreen(
                        sendCommitsIntentProvider = sendCommitsIntentProvider,
                        textContentProvider = textContentProvider
                    )
                }
            }
        }
    }
}
