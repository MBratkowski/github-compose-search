package io.bratexsoft.core.data.api.model

data class Verification(
    val payload: String,
    val reason: String,
    val signature: String,
    val verified: Boolean
)