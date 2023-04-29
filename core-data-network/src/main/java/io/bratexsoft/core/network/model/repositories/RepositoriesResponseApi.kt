package io.bratexsoft.core.network.model.repositories

import com.google.gson.annotations.SerializedName
import io.bratexsoft.core.data.api.model.Id
import io.bratexsoft.core.data.api.model.Name
import io.bratexsoft.core.data.api.model.Repositories

data class RepositoriesResponseApi(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
)
