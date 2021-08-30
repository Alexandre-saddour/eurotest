package fr.asaddour.eurotest.domain.news

import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun observeNews(): Flow<Pair<List<News.Story>, List<News.Video>>>

    suspend fun getStory(id: Int): News.Story?
    suspend fun refreshNews(): Boolean
}