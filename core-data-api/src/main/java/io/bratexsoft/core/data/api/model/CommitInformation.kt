package io.bratexsoft.core.data.api.model

import java.util.Date

data class CommitInformation(
    val date: Date,
    val message: String,
    val sha: String,
    val author: String
)
