package io.bratexsoft.core.network.model.commits

import com.google.gson.annotations.SerializedName
import io.bratexsoft.core.data.api.model.Parent

data class ParentResponseApi(
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("sha")
    val sha: String,
    @SerializedName("url")
    val url: String
)

fun ParentResponseApi.toDomain() = Parent(
    htmlUrl,
    sha,
    url
)
