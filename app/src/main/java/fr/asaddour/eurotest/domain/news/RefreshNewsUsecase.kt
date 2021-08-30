package fr.asaddour.eurotest.domain.news

import javax.inject.Inject

class RefreshNewsUsecase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend fun execute() = repository.refreshNews()

}