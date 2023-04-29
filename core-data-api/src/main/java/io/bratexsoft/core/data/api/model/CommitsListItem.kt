package io.bratexsoft.core.data.api.model

data class CommitsListItem(
    val author: Author,
    val commentsUrl: String,
    val commit: Commit,
    val committer: CommitterX,
    val htmlUrl: String,
    val nodeId: String,
    val parents: List<Parent>,
    val sha: String,
    val url: String
)
