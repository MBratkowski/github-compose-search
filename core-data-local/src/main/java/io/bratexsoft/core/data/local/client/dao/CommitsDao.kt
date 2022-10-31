package io.bratexsoft.core.data.local.client.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.bratexsoft.core.data.local.client.model.CommitInformationDb

@Dao
interface CommitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCommits(commitInformationDb: List<CommitInformationDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommit(commitInformationDb: CommitInformationDb)

    @Query("SELECT * FROM commitInformation WHERE repositoryId LIKE :repositoryId")
    suspend fun getAllCommitsFromRepository(repositoryId: Int): List<CommitInformationDb>
}