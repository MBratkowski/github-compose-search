package io.bratexsoft.core.network.model.repositories

import com.google.gson.annotations.SerializedName

data class RepositoriesResponseApi(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)
