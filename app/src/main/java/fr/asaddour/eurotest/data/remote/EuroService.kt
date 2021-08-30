package fr.asaddour.eurotest.data.remote

import fr.asaddour.eurotest.data.remote.news.NewsResponse
import retrofit2.http.GET

interface EuroService {

    @GET("bin/edfefba")
    suspend fun getNews(): NewsResponse
}
