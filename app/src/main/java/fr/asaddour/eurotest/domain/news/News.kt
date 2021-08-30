package fr.asaddour.eurotest.domain.news

import fr.asaddour.eurotest.domain.sport.Sport
import org.joda.time.DateTime

sealed interface News {
    val id: Int
    val date: DateTime
    val sport: Sport
    val title: String

    data class Story(
        override val id: Int,
        override val date: DateTime,
        override val sport: Sport,
        override val title: String,
        val author: String,
        val image: String,
        val teaser: String
    ): News

    data class Video(
        override val id: Int,
        override val date: DateTime,
        override val sport: Sport,
        override val title: String,
        val thumb: String,
        val url: String,
        val views: Int
    ): News

}