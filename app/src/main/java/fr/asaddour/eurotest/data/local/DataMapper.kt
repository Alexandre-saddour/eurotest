package fr.asaddour.eurotest.data.local

import fr.asaddour.eurotest.data.local.news.StoryNewsData
import fr.asaddour.eurotest.data.local.news.VideoNewsData
import fr.asaddour.eurotest.data.remote.news.NewsResponse
import fr.asaddour.eurotest.domain.news.News
import fr.asaddour.eurotest.domain.sport.Sport
import org.joda.time.DateTime

fun NewsResponse.Story.toData() = StoryNewsData(
    id = id,
    date = date.toLongDate(),
    sport = Sport(
        id = sport.id,
        name = sport.name,
    ),
    title = title,
    author = author,
    image = image,
    teaser = teaser
)

fun NewsResponse.Video.toData() = VideoNewsData(
    id = id,
    date = date.toLongDate(),
    sport = Sport(
        id = sport.id,
        name = sport.name,
    ),
    title = title,
    thumb = thumb,
    url = url,
    views = views
)

fun StoryNewsData.toDomain() = News.Story(
    id = id,
    date = DateTime(date),
    sport = sport,
    title = title,
    author = author,
    image = image,
    teaser = teaser
)

fun VideoNewsData.toDomain() = News.Video(
    id = id,
    date = DateTime(date),
    sport = sport,
    title = title,
    thumb = thumb,
    url = url,
    views = views
)

private fun Double.toLongDate() = (this.toBigDecimal() * 1000.toBigDecimal()).toLong()

