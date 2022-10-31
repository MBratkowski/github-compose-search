package io.bratexsoft.core.data.local.client.client

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.bratexsoft.core.data.local.client.converter.DateConverter
import io.bratexsoft.core.data.local.client.dao.CommitsDao
import io.bratexsoft.core.data.local.client.dao.RepositoriesDao
import io.bratexsoft.core.data.local.client.model.CommitInformationDb
import io.bratexsoft.core.data.local.client.model.RepositoryInformationDb

@Database(
    entities = [RepositoryInformationDb::class, CommitInformationDb::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class RepositoriesDatabaseClient : RoomDatabase() {
    abstract fun repositoriesDao(): RepositoriesDao
    abstract fun commitsDao(): CommitsDao
}