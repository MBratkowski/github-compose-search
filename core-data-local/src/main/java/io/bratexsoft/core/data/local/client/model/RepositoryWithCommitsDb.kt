package io.bratexsoft.core.data.local.client.model

import androidx.room.Embedded
import androidx.room.Relation

data class RepositoryWithCommitsDb(
    @Embedded val repositoryInformationDb: RepositoryInformationDb,
    @Relation(
        parentColumn = "id",
        entityColumn = "repositoryId"
    )
    val commitsInformationList: List<CommitInformationDb>
)
