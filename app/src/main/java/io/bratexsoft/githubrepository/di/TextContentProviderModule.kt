package io.bratexsoft.githubrepository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.bratexsoft.feature.searchRepositories.util.TextContentProvider
import io.bratexsoft.feature.searchRepositories.util.TextContentProviderImpl

@Module
@InstallIn(ActivityComponent::class)
interface TextContentProviderModule {

    @Binds
    fun bindsTextContentProvider(textContentProviderImpl: TextContentProviderImpl): TextContentProvider
}
