package fr.asaddour.eurotest.data.local.news

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.asaddour.eurotest.domain.sport.Sport

@Entity(tableName = "video_news")
data class VideoNewsData(
    @PrimaryKey
    val id: Int,
    val date: Long,
    @Embedded(prefix = "sport_")
    val sport: Sport,
    val title: String,
    val thumb: String,
    val url: String,
    val views: Int
){

}