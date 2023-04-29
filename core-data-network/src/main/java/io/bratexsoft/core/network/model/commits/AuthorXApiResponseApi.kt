package io.bratexsoft.core.network.model.commits


import com.google.gson.annotations.SerializedName
import io.bratexsoft.core.data.api.model.AuthorX
import java.util.*

data class AuthorXApiResponseApi(
    @SerializedName("date")
    val date: Date,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String
)

fun AuthorXApiResponseApi.toDomain() = AuthorX(
    date,
    email,
    name
)
