package io.bratexsoft.core.network.model.commits


import com.google.gson.annotations.SerializedName
import io.bratexsoft.core.data.api.model.CommitsListItem

data class CommitsListItemResponseApi(
    @SerializedName("author")
    val author: AuthorResponseApi,
    @SerializedName("comments_url")
    val commentsUrl: String,
    @SerializedName("commit")
    val commit: CommitResponseApi,
    @SerializedName("committer")
    val committer: CommitterXResponseApi,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("parents")
    val parents: List<ParentResponseApi>,
    @SerializedName("sha")
    val sha: String,
    @SerializedName("url")
    val url: String
)

fun CommitsListItemResponseApi.toDomain() = CommitsListItem(
    author.toDomain(),
    commentsUrl,
    commit.toDomain(),
    committer.toDomain(),
    htmlUrl,
    nodeId,
    parents.map { it.toDomain() },
    sha,
    url
)