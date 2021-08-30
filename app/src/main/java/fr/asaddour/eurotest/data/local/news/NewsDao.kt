package fr.asaddour.eurotest.data.local.news

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(storyNewsData: List<StoryNewsData>)

    @Query("SELECT * FROM story_news ORDER BY date DESC")
    fun observeStories(): Flow<List<StoryNewsData>>

    @Query("SELECT * FROM story_news WHERE story_news.id = :id")
    suspend fun getStoryById(id: Int): StoryNewsData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(storyNewsData: List<VideoNewsData>)

    @Query("SELECT * FROM video_news ORDER BY date DESC")
    fun observeVideos(): Flow<List<VideoNewsData>>

    @Query("SELECT * FROM video_news WHERE video_news.id = :id")
    suspend fun getVideoById(id: Int): VideoNewsData?

}