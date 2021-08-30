package fr.asaddour.eurotest.domain.news

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveNewsUsecase @Inject constructor(
    private val repository: NewsRepository
) {

    fun execute(): Flow<List<News>> = repository.observeNews().map { (stories, videos) ->
        when {
            stories.isEmpty() && videos.isEmpty() -> emptyList()
            stories.isNotEmpty() && videos.isEmpty() -> stories
            stories.isEmpty() && videos.isNotEmpty() -> videos
            else -> {
                // The most recent item between both lists will define if we start with
                // a video or a story.
                val firstStory = stories.first().date
                val firstVideo = videos.first().date
                when {
                    firstStory.isAfter(firstVideo) || firstStory == firstVideo -> merge(stories, videos)
                    else -> merge(videos, stories)
                }
            }
        }

    }

    fun merge(firstList: List<News>, secondList: List<News>): List<News> {
        val firstIt = firstList.iterator()
        val secondIt = secondList.iterator()
        val list = mutableListOf<News>()

        // [1,2,3] and [A,B,C] -> [1,A,2,B,3,C]
        while (firstIt.hasNext() && secondIt.hasNext()){
            list.add(firstIt.next())
            list.add(secondIt.next())
        }

        // get remaining items in case one was longer than the other
        return run {
            while (firstIt.hasNext()) {
                list.add(firstIt.next())
            }
            while (secondIt.hasNext()) {
                list.add(secondIt.next())
            }
            list
        }
    }

}