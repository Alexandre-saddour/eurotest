package fr.asaddour.eurotest.data.remote

import javax.inject.Inject

class RemoteClient @Inject constructor(
    private val euroService: EuroService
) {

    suspend fun getNews() = euroService.getNews()
}