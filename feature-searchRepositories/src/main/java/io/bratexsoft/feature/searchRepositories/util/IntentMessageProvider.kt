package io.bratexsoft.feature.searchRepositories.util

import io.bratexsoft.core.data.api.model.CommitInformation
import javax.inject.Inject

class IntentMessageProvider @Inject constructor(
    private val textContentProvider: TextContentProvider
) {

    operator fun invoke(commitsToShare: List<CommitInformation>): String {
        val stringBuilder: StringBuilder = StringBuilder()
        commitsToShare.forEach { commit ->
            with(commit) {
                stringBuilder.appendLine(textContentProvider.provideAuthor(author))
                stringBuilder.appendLine(textContentProvider.provideMessage(message))
                stringBuilder.appendLine(textContentProvider.provideSha(sha))
                stringBuilder.appendLine()
            }
        }
        return stringBuilder.toString()
    }
}
