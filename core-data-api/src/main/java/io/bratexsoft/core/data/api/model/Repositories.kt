package io.bratexsoft.core.data.api.model

data class Repositories(
    val id: Id,
    val name: Name
)

@JvmInline
value class Id(val value: Int)

@JvmInline
value class Name(val value: String)
