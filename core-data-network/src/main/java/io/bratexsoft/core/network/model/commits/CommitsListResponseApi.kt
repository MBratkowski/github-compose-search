package io.bratexsoft.core.network.model.commits

import io.bratexsoft.core.data.api.model.CommitList

data class CommitsListResponseApi(val commitListItemResponse: List<CommitsListItemResponseApi>)

fun CommitsListResponseApi.toDomain() =
    CommitList(commitList = commitListItemResponse.map { it.toDomain() })
