package fr.asaddour.eurotest.ui.news.list

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fr.asaddour.eurotest.R
import android.view.Window
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import fr.asaddour.eurotest.databinding.ActivityMainBinding
import fr.asaddour.eurotest.ui.utils.MarginItemDecoration
import fr.asaddour.eurotest.ui.news.details.StoryActivity
import fr.asaddour.eurotest.ui.news.details.VideoActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<NewsViewModel>()
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val newsAdapter = NewsAdapter { item, position ->
        viewModel.onItemClicked(item, position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTransition()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        viewModel.viewState.observe(this) { viewState ->
            updateView(viewState)
        }

        viewModel.events.observe(this) { event ->
            processEvent(event)
        }
    }

    private fun setupTransition() {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
    }

    private fun processEvent(event: NewsViewModel.ViewEvent) {
        when (event) {
            is NewsViewModel.ViewEvent.OpenStory -> {
                val child = requireNotNull((binding.newsList.layoutManager as LinearLayoutManager).findViewByPosition(event.position))
                startActivity(
                    StoryActivity.intent(
                        this,
                        event.id
                    ),
                    ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        child,
                        "newsContainer"
                    ).toBundle()
                )
            }

            is NewsViewModel.ViewEvent.OpenVideo -> startActivity(
                VideoActivity.intent(
                    this,
                    event.url
                )
            )
            NewsViewModel.ViewEvent.RefreshFailed -> Toast.makeText(
                this,
                R.string.network_error,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupView() {
        binding.newsList.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.recyclerViewVerticalEdges)))
        }
    }

    private fun updateView(viewState: NewsViewModel.ViewState) {
        newsAdapter.submitList(viewState.news)
    }

}