package fr.asaddour.eurotest.news

import fr.asaddour.eurotest.domain.news.News
import fr.asaddour.eurotest.domain.news.NewsRepository
import fr.asaddour.eurotest.domain.news.ObserveNewsUsecase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.joda.time.DateTime
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class ObserveNewsUsecaseTest {

    private val transactionRepository = mock<NewsRepository>()
    private val newsUsecase = ObserveNewsUsecase(transactionRepository)

    @Test
    fun `should start with story`() = runBlocking {

        val stories = listOf(
            emptyStory.copy(id = 1, date = DateTime()),
            emptyStory.copy(id = 2, date = DateTime().minusDays(1)),
            emptyStory.copy(id = 3, date = DateTime().minusDays(2)),
        )

        val videos = listOf(
            emptyVideo.copy(id = 1, date = DateTime().minusDays(3)),
            emptyVideo.copy(id = 2, date = DateTime().minusDays(4)),
            emptyVideo.copy(id = 3, date = DateTime().minusDays(5)),
        )

        whenever(transactionRepository.observeNews()).thenReturn(
            flowOf(Pair(stories, videos))
        )

        val news = newsUsecase.execute().toList().first().first()
        assertThat(news, Is(instanceOf(News.Story::class.java)))
    }

    @Test
    fun `should start with video`() = runBlocking {

        val stories = listOf(
            emptyStory.copy(id = 1, date = DateTime().minusDays(1)),
            emptyStory.copy(id = 2, date = DateTime().minusDays(2)),
            emptyStory.copy(id = 3, date = DateTime().minusDays(3)),
        )

        val videos = listOf(
            emptyVideo.copy(id = 1, date = DateTime()),
            emptyVideo.copy(id = 2, date = DateTime().minusDays(4)),
            emptyVideo.copy(id = 3, date = DateTime().minusDays(5)),
        )

        whenever(transactionRepository.observeNews()).thenReturn(
            flowOf(Pair(stories, videos))
        )

        val news = newsUsecase.execute().toList().first().first()
        assertThat(news, Is(instanceOf(News.Video::class.java)))
    }

    @Test
    fun `should have even odd ordering`() = runBlocking {

        val stories = listOf(
            emptyStory.copy(id = 1, date = DateTime()),
            emptyStory.copy(id = 2, date = DateTime().minusDays(1)),
            emptyStory.copy(id = 3, date = DateTime().minusDays(2)),
        )

        val videos = listOf(
            emptyVideo.copy(id = 1, date = DateTime().minusDays(3)),
            emptyVideo.copy(id = 2, date = DateTime().minusDays(4)),
            emptyVideo.copy(id = 3, date = DateTime().minusDays(5)),
        )

        whenever(transactionRepository.observeNews()).thenReturn(
            flowOf(Pair(stories, videos))
        )

        val news = newsUsecase.execute().toList().first()
        news.forEachIndexed { i, n ->
            when (i % 2) {
                0 -> assertThat(n, Is(instanceOf(News.Story::class.java)))
                else -> assertThat(n, Is(instanceOf(News.Video::class.java)))
            }
        }
    }

}