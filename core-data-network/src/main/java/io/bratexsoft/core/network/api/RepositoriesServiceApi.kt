package io.bratexsoft.core.network.api

import io.bratexsoft.core.network.model.commits.CommitsListItemResponseApi
import io.bratexsoft.core.network.model.repositories.RepositoriesResponseApi
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface RepositoriesServiceApi {

    @GET(value = "repos/{ownerName}/{repositoryName}")
    suspend fun getRepositories(
        @Path("ownerName") ownerName: String,
        @Path("repositoryName") repositoryName: String
    ): RepositoriesResponseApi

    @Headers("Authorization: Bearer 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")
    @GET(value = "repos/{ownerName}/{repositoryName}/commits")
    suspend fun getRepositoryCommits(
        @Path("ownerName") ownerName: String,
        @Path("repositoryName") repositoryName: String
    ): List<CommitsListItemResponseApi>
}
