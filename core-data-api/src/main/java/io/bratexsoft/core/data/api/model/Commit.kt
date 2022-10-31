package io.bratexsoft.core.data.api.model

data class Commit(
    val author: AuthorX,
    val commentCount: Int,
    val committer: Committer,
    val message: String,
    val tree: Tree,
    val url: String,
    val verification: Verification
)