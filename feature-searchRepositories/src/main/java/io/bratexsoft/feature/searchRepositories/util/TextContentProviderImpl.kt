package io.bratexsoft.feature.searchRepositories.util

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import io.bratexsoft.feature.searchRepositories.R
import javax.inject.Inject

class TextContentProviderImpl @Inject constructor(
    @ActivityContext val context: Context
) :
    TextContentProvider {
    override fun provideAuthor(author: String): String {
        return context.getString(
            R.string.search_repositories_author,
            author
        )
    }

    override fun provideMessage(message: String): String {
        return context.getString(
            R.string.search_repositories_message,
            message
        )
    }

    override fun provideSha(sha: String): String {
        return context.getString(
            R.string.search_repositories_sha,
            sha
        )
    }
}