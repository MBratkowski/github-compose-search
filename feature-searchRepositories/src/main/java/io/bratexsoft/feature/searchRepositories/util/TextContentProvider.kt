package io.bratexsoft.feature.searchRepositories.util

interface TextContentProvider {
    fun provideAuthor(author: String): String
    fun provideMessage(message: String): String
    fun provideSha(sha: String): String
}