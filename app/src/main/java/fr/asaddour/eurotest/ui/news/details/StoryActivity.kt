package fr.asaddour.eurotest.ui.news.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import dagger.hilt.android.AndroidEntryPoint
import fr.asaddour.eurotest.R
import fr.asaddour.eurotest.databinding.ActivityStoryBinding
import fr.asaddour.eurotest.ui.utils.TimeAgo

@AndroidEntryPoint
class StoryActivity : AppCompatActivity() {

    private val viewModel by viewModels<StoryViewModel>()
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityStoryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTransition()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        viewModel.viewState.observe(this) { viewState ->
            updateView(viewState)
        }

    }

    private fun setupTransition() {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        findViewById<View>(android.R.id.content).transitionName = "newsContainer"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 300L
        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 250L
        }
    }

    private fun setupView() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.back)
            setSupportActionBar(this)
        }

        requireNotNull(supportActionBar).apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_story_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateView(viewState: StoryViewModel.ViewState) {
        with(viewState.story ?: return) {
            Glide.with(this@StoryActivity).load(image).into(binding.newsImage)
            binding.title.text = title
            binding.sportName.text = sport
            binding.subTitle.text = run {
                val author = getString(R.string.by_author, author)
                val date = TimeAgo.getTimeAgo(date.millis)
                getString(R.string.pair_with_dash, author, date)
            }
            binding.storyContent.text = teaser
        }
    }

    companion object {

        fun intent(context: Context, storyId: Int) =
            Intent(context, StoryActivity::class.java).apply {
                putExtra(STORY_ID_KEY, storyId)
            }

        const val STORY_ID_KEY = "STORY_ID_KEY"

    }
}