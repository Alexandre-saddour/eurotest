package fr.asaddour.eurotest.news

import fr.asaddour.eurotest.domain.news.News
import fr.asaddour.eurotest.domain.sport.Sport
import org.joda.time.DateTime

internal val emptyStory = News.Story(
    id = 0,
    date = DateTime(),
    sport = Sport(id = 0, name = ""),
    title = "",
    author = "",
    image = "",
    teaser = ""
)

internal  val emptyVideo = News.Video(
    id = 0,
    date = DateTime(),
    sport = Sport(id = 0, name = ""),
    title = "",
    thumb = "",
    url = "",
    views = 0
)