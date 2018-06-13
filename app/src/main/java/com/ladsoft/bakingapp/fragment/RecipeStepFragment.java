package com.ladsoft.bakingapp.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.ladsoft.bakingapp.R;
import com.ladsoft.bakingapp.entity.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepFragment extends Fragment {
    public interface Callback {
        void onNextPress();
        void onPreviousPress();
        Step onVisible();
    }

    private static final String LOG_TAG = RecipeStepFragment.class.getSimpleName();
    private static final String STATE_DATASOURCE = "state_datasource";
    private static final String STATE_PLAYBACK_POSITION = "state_playback_position";
    private static final String STATE_PLAYBACK_WINDOW_POSITION = "state_playback_window_position";

    @BindView(R.id.next) Button stepNext;
    @BindView(R.id.previous) Button stepPrevious;

    @BindView(R.id.step_description) TextView stepDescription;

    @BindView(R.id.media_loading) ProgressBar videoLoading;
    @BindView(R.id.media_message) TextView mediaMessage;
    @BindView(R.id.media_player) SimpleExoPlayerView mediaPlayerView;

    private long playbackPosition;
    private Step datasource;
    private int currentWindow;
    private MediaSource mediaSource;
    private Callback listener;

    private boolean playWhenReady = false;
    private boolean isViewCreated = false;

    public static RecipeStepFragment newInstance() {
        return new RecipeStepFragment();
    }

    private Player.EventListener playerListener = new Player.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            Log.d(LOG_TAG, hashCode() + ": Playback position: " + playbackPosition);
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

        @Override
        public void onLoadingChanged(boolean isLoading) {
//            videoLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch(playbackState) {
                case Player.STATE_IDLE:
                    Log.d(LOG_TAG, hashCode() + ": STATE_IDLE plaback position " + playbackPosition);
                    break;

                case Player.STATE_BUFFERING:
                    Log.d(LOG_TAG, hashCode() + ": STATE_BUFFERING");
                    mediaMessage.setVisibility(View.GONE);
                    videoLoading.setVisibility(View.VISIBLE);
                    break;

                case Player.STATE_READY:
                    Log.d(LOG_TAG, hashCode() + ": STATE_READY, playback position " + playbackPosition);
                    mediaMessage.setVisibility(View.GONE);
                    videoLoading.setVisibility(View.GONE);
                    break;

                case Player.STATE_ENDED:
                    Log.d(LOG_TAG, hashCode() + ": STATE_ENDED");
                    mediaPlayer.setPlayWhenReady(false);
                    mediaPlayer.seekToDefaultPosition();
                    updateStartPosition(false);
                    break;
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {}

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.e(LOG_TAG, hashCode() + ": Player error", error);

            videoLoading.setVisibility(View.GONE);
            mediaMessage.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPositionDiscontinuity() {}

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (Callback) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, hashCode() + ": onCreateView: " + (savedInstanceState != null ? savedInstanceState.toString() : "NULL"));

        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;

        stepPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) { listener.onPreviousPress(); }
            }
        });

        stepNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) { listener.onNextPress(); }
            }
        });

        if (datasource == null) {
            stepDescription.setText(null);

            if (mediaSource != null) { mediaSource.releaseSource(); }
            if (mediaPlayer != null) { mediaPlayer.clearVideoSurface(); }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, hashCode() + ": onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            this.datasource = savedInstanceState.getParcelable(STATE_DATASOURCE);
            this.playbackPosition = savedInstanceState.getLong(STATE_PLAYBACK_POSITION);
            this.currentWindow = savedInstanceState.getInt(STATE_PLAYBACK_WINDOW_POSITION);
        }
    }

    @Override
    public void onStart() {
        Log.d(LOG_TAG, hashCode() + ": onStart");
        super.onStart();

        if (Util.SDK_INT > 23 && playWhenReady) {
            bindDatasource();
        }
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, hashCode() + ": onResume");
        super.onResume();

        if (Util.SDK_INT <= 23 && playWhenReady) {
            bindDatasource();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(LOG_TAG, hashCode() + ": onSavedInstanceState");

        releasePlayer(true);
        outState.putParcelable(STATE_DATASOURCE, datasource);
        outState.putLong(STATE_PLAYBACK_POSITION, playbackPosition);
        outState.putInt(STATE_PLAYBACK_WINDOW_POSITION, currentWindow);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(LOG_TAG, hashCode() + ": setUserVisibleHint: " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);

        playWhenReady = isVisibleToUser;
        boolean isResumed = isResumed();
        if (playWhenReady) {
            bindDatasource();
        } else if (isResumed) {
            releasePlayer(true);
        }
    }

    @Override
    public void onPause() {
        Log.d(LOG_TAG, hashCode() + ": onPause");
        if (Util.SDK_INT <= 23) {
            releasePlayer(true);
        }

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(LOG_TAG, hashCode() + ": onStop");
        if (Util.SDK_INT > 23) {
            releasePlayer(true);
        }
        super.onStop();
    }

    SimpleExoPlayer mediaPlayer;
    private void initializePlayer() {
        if(mediaPlayer == null) {
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(getContext());
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mediaPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            mediaPlayer.addListener(playerListener);
            mediaPlayerView.setPlayer(mediaPlayer);

            if (playWhenReady) {
                Log.d(LOG_TAG,  hashCode() + ": initializePlayer: autoPlaying media at position "
                            + playbackPosition + ". " + (datasource != null ? datasource: "NULL"));
            }

            mediaPlayer.setPlayWhenReady(playWhenReady);

            mediaPlayer.seekTo(currentWindow, playbackPosition);
            mediaPlayerView.hideController();
        }

        if (mediaSource != null) {
            mediaPlayer.prepare(mediaSource, true, false);
        }
    }

    private void releasePlayer(boolean keepPlaybackPosition) {
        if (mediaPlayer == null) { return; }

        updateStartPosition(keepPlaybackPosition);

        mediaPlayer.removeListener(playerListener);
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        return new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
    }

    public void setListener(Callback callback) {
        listener = callback;
    }

    private void bindDatasource() {
        datasource = listener.onVisible();

        if (!isViewCreated) { return; }

        if (this.datasource == null) {
            stepDescription.setText(null);

            if (mediaSource != null) {
                mediaSource.releaseSource();
                mediaPlayer.clearVideoSurface();
            }
        } else {
            Log.d(LOG_TAG, hashCode() + ": datasource: " + datasource.toString());

            stepDescription.setText(this.datasource.getDescription());

            if (this.datasource.getVideoUrl() != null) {
                Uri mediaUri = Uri.parse(this.datasource.getVideoUrl());
                Log.d(LOG_TAG, hashCode() + ": Step media Uri: " + mediaUri.toString());

                mediaSource = buildMediaSource(mediaUri);
                initializePlayer();
            }
        }
    }

    private void updateStartPosition(boolean keepPlaybackPosition) {
        if (mediaPlayer == null) {return;}

        playWhenReady = mediaPlayer.getPlayWhenReady();
        currentWindow = mediaPlayer.getCurrentWindowIndex();
        playbackPosition = keepPlaybackPosition ? Math.max(0L, mediaPlayer.getContentPosition()) : 0L;
    }
}
