package fr.asaddour.eurotest.ui.news.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.asaddour.eurotest.domain.news.News
import fr.asaddour.eurotest.domain.news.ObserveNewsUsecase
import fr.asaddour.eurotest.domain.news.RefreshNewsUsecase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val observeNewsUsecase: ObserveNewsUsecase,
    private val refreshNewsUsecase: RefreshNewsUsecase
) : ViewModel() {

    private val _events = LiveEvent<ViewEvent>()
    val events: LiveData<ViewEvent> = _events

    private val _viewState = MutableLiveData(ViewState(news = emptyList()))
    val viewState: LiveData<ViewState> = _viewState

    init {
        viewModelScope.launch {
            observeNewsUsecase.execute().collect { news ->
                _viewState.value = requireNotNull(viewState.value).copy(
                    news = news.map { it.toUi() }
                )
            }
        }

        viewModelScope.launch {
            val isSuccessful = refreshNewsUsecase.execute()
            if (!isSuccessful) {
                _events.value = ViewEvent.RefreshFailed
            }
        }
    }

    fun onItemClicked(item: NewsAdapter.Item, itemPosition: Int) {
        _events.value = when (item){
            is NewsAdapter.Item.Story -> ViewEvent.OpenStory(item.id, itemPosition)
            is NewsAdapter.Item.Video -> ViewEvent.OpenVideo(item.url)
        }
    }

    private fun News.toUi() = when (this) {
        is News.Story -> NewsAdapter.Item.Story(
            id = id,
            date = date,
            sport = sport.name,
            title = title,
            author = author,
            image = image
        )
        is News.Video -> NewsAdapter.Item.Video(
            id = id,
            sport = sport.name,
            title = title,
            thumb = thumb,
            url = url,
            views = views.toString()
        )
    }

    data class ViewState(
        val news: List<NewsAdapter.Item>
    )

    sealed interface ViewEvent {
        data class OpenStory(val id: Int, val position: Int) : ViewEvent
        data class OpenVideo(val url: String) : ViewEvent

        object RefreshFailed : ViewEvent
    }
}
