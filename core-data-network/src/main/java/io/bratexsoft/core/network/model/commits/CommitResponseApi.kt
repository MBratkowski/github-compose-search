package io.bratexsoft.core.network.model.commits

import com.google.gson.annotations.SerializedName
import io.bratexsoft.core.data.api.model.Commit

data class CommitResponseApi(
    @SerializedName("author")
    val author: AuthorXApiResponseApi,
    @SerializedName("comment_count")
    val commentCount: Int,
    @SerializedName("committer")
    val committer: CommitterResponseApi,
    @SerializedName("message")
    val message: String,
    @SerializedName("tree")
    val tree: TreeResponseApi,
    @SerializedName("url")
    val url: String,
    @SerializedName("verification")
    val verification: VerificationResponseApi
)

fun CommitResponseApi.toDomain() = Commit(
    author.toDomain(),
    commentCount,
    committer.toDomain(),
    message,
    tree.toDomain(),
    url,
    verification.toDomain()
)
