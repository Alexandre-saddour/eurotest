package fr.asaddour.eurotest.domain.news

import javax.inject.Inject

class GetStoryUsecase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend fun execute(id: Int) = repository.getStory(id)
}