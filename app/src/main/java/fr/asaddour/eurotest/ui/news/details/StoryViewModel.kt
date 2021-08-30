package fr.asaddour.eurotest.ui.news.details

import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.asaddour.eurotest.domain.news.GetStoryUsecase
import fr.asaddour.eurotest.domain.news.News
import fr.asaddour.eurotest.domain.news.ObserveNewsUsecase
import fr.asaddour.eurotest.domain.news.RefreshNewsUsecase
import fr.asaddour.eurotest.ui.news.details.StoryActivity.Companion.STORY_ID_KEY
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val getStoryUsecase: GetStoryUsecase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = MutableLiveData(ViewState(story = null))
    val viewState: LiveData<ViewState> = _viewState

    val storyId: Int = savedStateHandle.get(STORY_ID_KEY) ?: throw IllegalArgumentException(
        "STORY_ID_KEY should not be empty"
    )

    init {
        viewModelScope.launch {
            getStoryUsecase.execute(storyId)?.let { story ->
                _viewState.value = requireNotNull(viewState.value).copy(
                    story = story.toUi()
                )
            }
        }
    }

    private fun News.Story.toUi() = StoryUi(
        date = date,
        sport = sport.name,
        title = title,
        author = author,
        teaser = teaser,
        image = image
    )

    data class ViewState(
        val story: StoryUi?
    )

    data class StoryUi(
        val date: DateTime,
        val sport: String,
        val title: String,
        val author: String,
        val teaser: String,
        val image: String,
    )

}
