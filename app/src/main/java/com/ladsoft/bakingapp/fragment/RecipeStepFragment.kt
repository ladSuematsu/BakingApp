package com.ladsoft.bakingapp.fragment


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.ladsoft.bakingapp.R
import com.ladsoft.bakingapp.entity.Step

class RecipeStepFragment : Fragment() {

    @JvmField @BindView(R.id.next)
    var stepNext: Button? = null

    @JvmField @BindView(R.id.previous)
    var stepPrevious: Button? = null

    @JvmField @BindView(R.id.step_description)
    var stepDescription: TextView? = null

    @JvmField @BindView(R.id.media_player)
    var mediaPlayerView: SimpleExoPlayerView? = null

    private var playbackPosition: Long = 0L
    private var datasource: Step? = null
    private var currentWindow: Int = 0
    private var mediaSource: MediaSource? = null
    private var listener: Callback? = null

    private var playWhenReady = false
    private var isViewCreated = false

    private var mediaPlayer: SimpleExoPlayer? = null

    private val playerListener = object : Player.EventListener {

        override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
            playbackPosition = mediaPlayer?.currentPosition ?: 0L
            Log.d(LOG_TAG, hashCode().toString() + ": Playback position: " + playbackPosition)
        }

        override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray) {}

        override fun onLoadingChanged(isLoading: Boolean) {}

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {
                Player.STATE_ENDED -> {
                    mediaPlayer?.playWhenReady = false
                    mediaPlayer?.seekToDefaultPosition()
                }
            }
        }

        override fun onRepeatModeChanged(repeatMode: Int) {}

        override fun onPlayerError(error: ExoPlaybackException) {}

        override fun onPositionDiscontinuity() {}

        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
    }

    interface Callback {
        fun onNextPress()
        fun onPreviousPress()
        fun onVisible(): Step?
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        listener = activity as Callback?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(LOG_TAG, hashCode().toString() + ": onCreateView: " + (savedInstanceState?.toString()
                ?: "NULL"))

        val view = inflater.inflate(R.layout.fragment_recipe_step, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true

        stepPrevious?.setOnClickListener {
            listener?.onPreviousPress()
        }

        stepNext?.setOnClickListener { listener?.onNextPress()
        }

        if (datasource == null) {
            stepDescription?.text = null

            mediaSource?.releaseSource()
            mediaPlayer?.clearVideoSurface()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, hashCode().toString() + ": onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            this.datasource = savedInstanceState.getParcelable(STATE_DATASOURCE)
            this.playbackPosition = savedInstanceState.getLong(STATE_PLAYBACK_POSITION)
        }
    }

    override fun onStart() {
        Log.d(LOG_TAG, hashCode().toString() + ": onStart")
        super.onStart()

        if (Util.SDK_INT > 23 && playWhenReady) {
            bindDatasource()
        }
    }

    override fun onResume() {
        Log.d(LOG_TAG, hashCode().toString() + ": onResume")
        super.onResume()

        if (Util.SDK_INT <= 23 && playWhenReady) {
            bindDatasource()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(LOG_TAG, hashCode().toString() + ": onSavedInstanceState")
        super.onSaveInstanceState(outState)

        outState.putParcelable(STATE_DATASOURCE, datasource)
        outState.putLong(STATE_PLAYBACK_POSITION, playbackPosition)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        Log.d(LOG_TAG, hashCode().toString() + ": setUserVisibleHint: " + isVisibleToUser)
        super.setUserVisibleHint(isVisibleToUser)

        playWhenReady = isVisibleToUser
        val isResumed = isResumed
        if (playWhenReady) {
            bindDatasource()
        } else if (isResumed) {
            releasePlayer(false)
        }
    }

    override fun onPause() {
        Log.d(LOG_TAG, hashCode().toString() + ": onPause")

        if (Util.SDK_INT <= 23) {
            releasePlayer(true)
        }

        super.onPause()
    }

    override fun onStop() {
        Log.d(LOG_TAG, hashCode().toString() + ": onStop")

        if (Util.SDK_INT > 23) {
            releasePlayer(true)
        }

        super.onStop()
    }

    private fun initializePlayer() {
        if (mediaPlayer == null) {
            val renderersFactory = DefaultRenderersFactory(context)
            val trackSelector = DefaultTrackSelector()
            val loadControl = DefaultLoadControl()

            mediaPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl)
            mediaPlayer?.addListener(playerListener)

            if (playWhenReady) {
                Log.d(LOG_TAG, hashCode().toString() + ": initializePlayer: autoPlaying media  STEP ID "
                        + (datasource?.id ?: "NULL"))
            }

            mediaPlayerView?.player = mediaPlayer

            mediaPlayer?.playWhenReady = true

            mediaPlayer?.seekTo(currentWindow, playbackPosition)
            mediaPlayerView?.hideController()
        }

        mediaPlayer?.prepare(mediaSource, true, false)
    }

    private fun releasePlayer(keepPlaybackPosition: Boolean) {
        if (mediaPlayer != null) {
            playbackPosition =
                    if (keepPlaybackPosition && mediaPlayer != null)
                        mediaPlayer!!.currentPosition else 0L

            currentWindow = mediaPlayer?.currentWindowIndex ?: 0
            playWhenReady = mediaPlayer?.playWhenReady ?: false

            mediaPlayer?.removeListener(playerListener)
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER)
        val extractorsFactory = DefaultExtractorsFactory()

        return ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null)
    }

    fun setListener(callback: Callback) {
        listener = callback
    }

    private fun bindDatasource() {
        datasource = listener?.onVisible()

        if (!isViewCreated) {
            return
        }

        if (this.datasource == null) {
            stepDescription?.text = null

            mediaSource?.releaseSource()
            mediaPlayer?.clearVideoSurface()
        } else {
            Log.d(LOG_TAG, hashCode().toString() + ": datasource: " + datasource!!.toString())

            stepDescription?.text = this.datasource?.description

            if (this.datasource?.videoUrl != null) {
                val mediaUri = Uri.parse(this.datasource?.videoUrl)
                Log.d(LOG_TAG, hashCode().toString() + ": Step media Uri: " + mediaUri.toString())

                mediaSource = buildMediaSource(mediaUri)
                initializePlayer()
            }
        }
    }

    companion object {
        private val LOG_TAG = RecipeStepFragment::class.java.simpleName
        private const val STATE_DATASOURCE = "state_datasource"
        private const val STATE_PLAYBACK_POSITION = "state_playback_position"

        fun newInstance(): RecipeStepFragment {
            return RecipeStepFragment()
        }

        private val BANDWIDTH_METER = DefaultBandwidthMeter()
    }
}
