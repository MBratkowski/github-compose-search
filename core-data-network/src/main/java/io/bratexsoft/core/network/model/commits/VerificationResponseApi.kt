package io.bratexsoft.core.network.model.commits


import com.google.gson.annotations.SerializedName
import io.bratexsoft.core.data.api.model.Verification

data class VerificationResponseApi(
    @SerializedName("payload")
    val payload: String?,
    @SerializedName("reason")
    val reason: String,
    @SerializedName("signature")
    val signature: String?,
    @SerializedName("verified")
    val verified: Boolean
)

fun VerificationResponseApi.toDomain() = Verification(
    payload = payload ?: "",
    reason = reason,
    signature = signature ?: "",
    verified = verified
)
