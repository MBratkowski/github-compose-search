package io.bratexsoft.feature.searchRepositories

import io.bratexsoft.core.data.api.model.CommitInformation
import io.bratexsoft.feature.searchRepositories.util.IntentMessageProvider
import io.bratexsoft.feature.searchRepositories.util.TextContentProvider
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*


class IntentMessageProviderTest {

    lateinit var sut: IntentMessageProvider


    private val commitsToShare: List<CommitInformation> = mutableListOf(
        CommitInformation(
            date = Date(),
            message = "Hello world",
            author = "Mateusz",
            sha = "1"
        ), CommitInformation(
            date = Date(),
            message = "Hello world 2",
            author = "Mateusz 2",
            sha = "2"
        )
    )

    @Test
    fun shouldReturnEmptyListForEmptyCommitList() {
        //Arrange
        sut = IntentMessageProvider(TextContentProviderFake())

        //Verify
        val result = sut(emptyList())

        //Assert
        assertEquals(result, "")
    }

    @Test
    fun verifyIfTextIsParsedProperly() {
        //Arrange
        sut = IntentMessageProvider(TextContentProviderFake())

        //Verify
        val result = sut(commitsToShare)

        //Assert
        assertEquals(result, expectedParseResult)
    }
}

const val expectedParseResult = "Author: Mateusz\n" +
        "Message: Hello world\n" +
        "Sha: 1\n" +
        "\n" +
        "Author: Mateusz 2\n" +
        "Message: Hello world 2\n" +
        "Sha: 2\n" +
        "\n"

class TextContentProviderFake : TextContentProvider {
    override fun provideAuthor(author: String): String {
        return "Author: $author"
    }

    override fun provideMessage(message: String): String {
        return "Message: $message"
    }

    override fun provideSha(sha: String): String {
        return "Sha: $sha"
    }

}