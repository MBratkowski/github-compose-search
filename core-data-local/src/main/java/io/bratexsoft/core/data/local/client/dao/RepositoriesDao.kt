package io.bratexsoft.core.data.local.client.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.bratexsoft.core.data.local.client.model.RepositoryInformationDb

@Dao
interface RepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepository(repositoryInformationDb: RepositoryInformationDb)

    @Query("SELECT * FROM repositoryInformation")
    suspend fun getRepositoryWithCommits(): List<RepositoryInformationDb>

}
