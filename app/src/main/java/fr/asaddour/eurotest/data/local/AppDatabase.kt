package fr.asaddour.eurotest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.asaddour.eurotest.data.local.news.NewsDao
import fr.asaddour.eurotest.data.local.news.StoryNewsData
import fr.asaddour.eurotest.data.local.news.VideoNewsData

@Database(
    entities = [
        StoryNewsData::class,
        VideoNewsData::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}