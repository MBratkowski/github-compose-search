package io.bratexsoft.core.network.model.repositories


import com.google.gson.annotations.SerializedName
import io.bratexsoft.core.data.api.model.Permissions

data class PermissionsResponseApi(
    @SerializedName("admin")
    val admin: Boolean,
    @SerializedName("maintain")
    val maintain: Boolean,
    @SerializedName("pull")
    val pull: Boolean,
    @SerializedName("push")
    val push: Boolean,
    @SerializedName("triage")
    val triage: Boolean
)

fun PermissionsResponseApi.toDomain() = Permissions(
    admin,
    maintain,
    pull,
    push,
    triage
)
