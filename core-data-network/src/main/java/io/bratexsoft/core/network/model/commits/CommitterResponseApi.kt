package io.bratexsoft.core.network.model.commits

import com.google.gson.annotations.SerializedName
import io.bratexsoft.core.data.api.model.Committer

data class CommitterResponseApi(
    @SerializedName("date")
    val date: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String
)

fun CommitterResponseApi.toDomain() = Committer(
    date,
    email,
    name
)
