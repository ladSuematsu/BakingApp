package com.ladsoft.bakingapp.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private static final String TAG = RecipeStepFragment.class.getSimpleName();

    @BindView(R.id.next) Button stepNext;
    @BindView(R.id.previous) Button stepPrevious;
    @BindView(R.id.step_description) TextView stepDescription;

    @BindView(R.id.media_player) SimpleExoPlayerView mediaPlayerView;

    private long playbackPosition;

    private Step datasource;
    private int currentWindow;
    private boolean playWhenReady = true;
    private MediaSource mediaSource;
    private Callback listener;
    private static final String STATE_STEP = "STATE_STEP";

    public static RecipeStepFragment newInstance() {
        return new RecipeStepFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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
        } else {
            stepDescription.setText(datasource.getDescription());

            if (datasource.getVideoUrl() != null) {
                Uri mediaUri = Uri.parse(datasource.getVideoUrl());
                Log.d(TAG, "Step media Uri: " + mediaUri.toString());

                mediaSource = buildMediaSource(mediaUri);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            setDatasource((Step) savedInstanceState.getParcelable(STATE_STEP));
        }
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart STEP ID " + datasource.getId());
        super.onStart();

        if (Util.SDK_INT > 23 && isVisible()) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume STEP ID " + datasource.getId());
        super.onResume();

        if (Util.SDK_INT <= 23 && isVisible()) {
            initializePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_STEP, datasource);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Log.d(TAG, "user visible hint STEP ID " + datasource.getId() + " " + isVisibleToUser);

        playWhenReady = isVisibleToUser;
        if (isVisibleToUser) {
            if (mediaPlayer != null) {
                Log.d(TAG, "user visible hint autoplaying STEP ID " + datasource.getId());
                mediaPlayer.setPlayWhenReady(playWhenReady);
            }
        } else {
            if (mediaPlayer != null) {
                Log.d(TAG, "user visible hint autoPausing STEP ID " + datasource.getId());

                mediaPlayer.setPlayWhenReady(false);
            }
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause STEP ID " + datasource.getId());
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop STEP ID " + datasource.getId());
        if (Util.SDK_INT > 23) {
            releasePlayer();
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

            if (playWhenReady) {
                Log.d(TAG, "initializePlayer: autoPlaying media  STEP ID " + datasource.getId());
            }
            mediaPlayerView.setPlayer(mediaPlayer);

            mediaPlayer.setPlayWhenReady(playWhenReady);
            mediaPlayer.seekTo(currentWindow, playbackPosition);
            mediaPlayerView.hideController();
        }

        if (mediaSource != null) {
            mediaPlayer.prepare(mediaSource, true, false);
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            playbackPosition = mediaPlayer.getCurrentPosition();
            currentWindow = mediaPlayer.getCurrentWindowIndex();
            playWhenReady = mediaPlayer.getPlayWhenReady();

            mediaPlayer.removeListener(playerListener);
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        return new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
    }

    private Player.EventListener playerListener = new Player.EventListener() {

        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {}

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

        @Override
        public void onLoadingChanged(boolean isLoading) {}

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            PlaybackStateCompat.Builder builder = new PlaybackStateCompat.Builder();

            switch(playbackState) {
                case Player.STATE_ENDED:
                    mediaPlayer.setPlayWhenReady(false);
                    mediaPlayer.seekToDefaultPosition();
                    break;
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {}

        @Override
        public void onPlayerError(ExoPlaybackException error) {}

        @Override
        public void onPositionDiscontinuity() {}

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
    };

    public void setListener(Callback callback) {
        listener = callback;
    }

    public void setDatasource(Step datasource) {
        this.datasource = datasource;

        if (isVisible()) {
            if (datasource == null) {
                stepDescription.setText(null);

                if (mediaSource != null) {
                    mediaSource.releaseSource();
                    mediaPlayer.clearVideoSurface();
                }
            } else {
                stepDescription.setText(datasource.getDescription());

                if (datasource.getVideoUrl() != null) {
                    Uri mediaUri = Uri.parse(datasource.getVideoUrl());
                    Log.d(TAG, "Step media Uri: " + mediaUri.toString());

                    mediaSource = buildMediaSource(mediaUri);
                    initializePlayer();
                }
            }
        }
    }

    public interface Callback {
        void onNextPress();
        void onPreviousPress();
    }
}
