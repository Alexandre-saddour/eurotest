package fr.asaddour.eurotest.data.remote.news

data class NewsResponse(
    val stories: List<Story>,
    val videos: List<Video>
){

    data class Story(
        val id: Int,
        val date: Double,
        val sport: Sport,
        val title: String,
        val author: String,
        val image: String,
        val teaser: String
    )

    data class Video(
        val id: Int,
        val date: Double,
        val sport: Sport,
        val title: String,
        val thumb: String,
        val url: String,
        val views: Int
    )

    data class Sport(
        val id: Int,
        val name: String
    )

}

