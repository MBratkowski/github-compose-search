package io.bratexsoft.core.data.local.client.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "commitInformation")
data class CommitInformationDb(
    @PrimaryKey val sha: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "repositoryId") val repositoryId: Int
)
