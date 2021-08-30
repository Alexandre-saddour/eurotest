package fr.asaddour.eurotest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.asaddour.eurotest.data.local.news.NewsDao
import fr.asaddour.eurotest.data.local.news.NewsRepositoryImpl
import fr.asaddour.eurotest.data.remote.RemoteClient
import fr.asaddour.eurotest.domain.news.NewsRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideWalletRepository(
        remoteClient: RemoteClient,
        dao: NewsDao
    ): NewsRepository = NewsRepositoryImpl(
        remoteSource = remoteClient,
        localSource = dao
    )

}
