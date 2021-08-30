package fr.asaddour.eurotest.ui.news.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint
import fr.asaddour.eurotest.databinding.ActivityVideoBinding

@AndroidEntryPoint
class VideoActivity : AppCompatActivity() {

    private var player: SimpleExoPlayer? = null
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityVideoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer
                val mediaItem = MediaItem.fromUri(requireNotNull(intent.getStringExtra(URL_KEY)))
                exoPlayer.apply {
                    setMediaItem(mediaItem)
                    playWhenReady = true
                    prepare()
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        player?.run {
            release()
        }
        player = null
    }

    companion object {

        fun intent(context: Context, url: String) = Intent(context, VideoActivity::class.java).apply {
            putExtra(URL_KEY, url)
        }

        private const val URL_KEY = "URL_KEY"

    }
}