package io.bratexsoft.core.network.model.commits


import com.google.gson.annotations.SerializedName
import io.bratexsoft.core.data.api.model.Tree

data class TreeResponseApi(
    @SerializedName("sha")
    val sha: String,
    @SerializedName("url")
    val url: String
)

fun TreeResponseApi.toDomain() = Tree(
    sha,
    url
)