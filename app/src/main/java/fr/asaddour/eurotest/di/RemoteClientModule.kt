package fr.asaddour.eurotest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.asaddour.eurotest.data.remote.EuroService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteClientModule {

    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder().apply {
        baseUrl("https://extendsclass.com/api/json-storage/")
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    @Singleton
    @Provides
    fun provideEuroService(retrofit: Retrofit) = retrofit.create(EuroService::class.java)

}