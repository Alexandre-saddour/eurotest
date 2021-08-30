package fr.asaddour.eurotest.data.local.news

import fr.asaddour.eurotest.data.local.toData
import fr.asaddour.eurotest.data.local.toDomain
import fr.asaddour.eurotest.data.remote.RemoteClient
import fr.asaddour.eurotest.domain.news.News
import fr.asaddour.eurotest.domain.news.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class NewsRepositoryImpl(
    private val remoteSource: RemoteClient,
    private val localSource: NewsDao,
) : NewsRepository {

    override fun observeNews(): Flow<Pair<List<News.Story>, List<News.Video>>> {
        return localSource.observeStories().combine(localSource.observeVideos()) { s, v ->
            Pair(s.map { it.toDomain() }, v.map { it.toDomain() })
        }
    }

    override suspend fun getStory(id: Int): News.Story? {
        return localSource.getStoryById(id)?.toDomain()
    }

    override suspend fun refreshNews(): Boolean {
        return try {
            val news = remoteSource.getNews()
            localSource.insertStories(news.stories.map { it.toData() })
            localSource.insertVideos(news.videos.map { it.toData() })
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}