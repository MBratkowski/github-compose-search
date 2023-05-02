package io.bratexsoft.core.network

import com.google.common.truth.Truth.assertThat
import io.bratexsoft.core.data.api.model.Repositories
import io.bratexsoft.core.network.mapper.RepositoriesMapper
import io.bratexsoft.core.network.model.repositories.RepositoriesResponseApi
import org.junit.Before
import org.junit.Test
import java.util.UUID
import kotlin.random.Random

class RepositoryMapperTest {

    private lateinit var repositoriesMapper: RepositoriesMapper

    @Before
    fun setup() {
        repositoriesMapper = RepositoriesMapper()
    }

    @Test
    fun `create a repository mapper with success`() {
        assertThat(repositoriesMapper).isNotNull()
        assertThat(repositoriesMapper).isInstanceOf(RepositoriesMapper::class.java)
    }

    @Test
    fun `method mapTo returns repository object`() {
        val repositoriesResponseApi = RepositoriesResponseApi(
            id = randomInt(),
            name = randomString()
        )
        val repository = repositoriesMapper.mapTo(repositoriesResponseApi = repositoriesResponseApi)

        assertThat(repository).isNotNull()
        assertThat(repository).isInstanceOf(Repositories::class.java)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `method mapTo throw exception when id is null`() {
        val repositoriesResponseApi = RepositoriesResponseApi(
            id = null,
            name = randomString()
        )

        repositoriesMapper.mapTo(repositoriesResponseApi)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `method mapTo throw exception when name is null`() {
        val repositoriesResponseApi = RepositoriesResponseApi(
            id = randomInt(),
            name = null
        )
        repositoriesMapper.mapTo(repositoriesResponseApi)
    }

    @Test
    fun `method mapTo returns repository object with parsed data from response`() {
        val repositoryResponseApi = RepositoriesResponseApi(
            id = randomInt(),
            name = randomString()
        )

        val repository = repositoriesMapper.mapTo(repositoryResponseApi)

        assertThat(repository.id.value).isEqualTo(repositoryResponseApi.id)
        assertThat(repository.name.value).isEqualTo(repositoryResponseApi.name)
    }
}

fun randomInt(): Int {
    return Random.nextInt()
}

fun randomString(): String {
    return UUID.randomUUID().toString()
}
