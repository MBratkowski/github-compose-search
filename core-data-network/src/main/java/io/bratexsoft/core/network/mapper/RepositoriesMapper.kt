package io.bratexsoft.core.network.mapper

import io.bratexsoft.core.data.api.model.Id
import io.bratexsoft.core.data.api.model.Name
import io.bratexsoft.core.data.api.model.Repositories
import io.bratexsoft.core.network.model.repositories.RepositoriesResponseApi
import javax.inject.Inject

class RepositoriesMapper @Inject constructor() {

    fun mapTo(repositoriesResponseApi: RepositoriesResponseApi? = null): Repositories {
        return Repositories(
            id = Id(value = requireNotNull(repositoriesResponseApi?.id) { "Id can't be a null" }),
            name = Name(value = requireNotNull(repositoriesResponseApi?.name) { "Name can't be a null" })
        )
    }
}