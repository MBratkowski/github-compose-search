package io.bratexsoft.core.data.local.client.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositoryInformation")
data class RepositoryInformationDb(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "repositoryName") val repositoryName: String,
)
