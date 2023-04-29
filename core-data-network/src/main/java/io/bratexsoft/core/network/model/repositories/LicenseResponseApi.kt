package io.bratexsoft.core.network.model.repositories


import com.google.gson.annotations.SerializedName
import io.bratexsoft.core.data.api.model.License

data class LicenseResponseApi(
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("spdx_id")
    val spdxId: String,
    @SerializedName("url")
    val url: String
)

fun LicenseResponseApi.toDomain() = License(
    key,
    name,
    nodeId,
    spdxId,
    url
)
