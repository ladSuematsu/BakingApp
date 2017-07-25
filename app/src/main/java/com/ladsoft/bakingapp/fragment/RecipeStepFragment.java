package com.ladsoft.bakingapp.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
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

    @BindView(R.id.step_description) TextView stepDescription;

    @BindView(R.id.media_player) SimpleExoPlayerView mediaPlayerView;

    private long playbackPosition;

    private int currentWindow;
    private boolean playWhenReady = true;
    private MediaSource mediaSource;
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
    public void onStart() {
        Log.i("STEP_VID_PLAYER", "onStart");
        super.onStart();

        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        Log.i("STEP_VID_PLAYER", "onResume");
        super.onResume();

        if (Util.SDK_INT <= 23) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        Log.i("STEP_VID_PLAYER", "onPause");
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        Log.i("STEP_VID_PLAYER", "onStop");
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    SimpleExoPlayer mediaPlayer;
    MediaSessionCompat mediaSession;
    PlaybackStateCompat.Builder playbackStateBuilder;
    private void initializePlayer() {
        if(mediaPlayer == null) {
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(getContext());
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mediaPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            mediaPlayer.addListener(playerListener);

            mediaPlayerView.setPlayer(mediaPlayer);

            mediaPlayer.setPlayWhenReady(playWhenReady);
            mediaPlayer.seekTo(currentWindow, playbackPosition);
            mediaPlayerView.hideController();
        }

//        MediaSource mediaSource = buildMediaSource(Uri.parse(getString(R.string.media_url_dash)));

        if (mediaSource != null) {
            mediaPlayer.prepare(mediaSource, true, false);
        }

//        String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
//        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
//                this, userAgent), new DefaultExtractorsFactory(), null, null);
//        mExoPlayer.prepare(mediaSource);
//        mExoPlayer.setPlayWhenReady(true);
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

//        DashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(dataSourceFactory);
//        return new DashMediaSource(uri, dataSourceFactory, dashChunkSourceFactory, null, null);
        return new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
    }

//    private void initializeMediaSession() {
//        mediaSession = new MediaSessionCompat(getContext(), TAG);
//        mediaSession.setFlags(
//                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
//                    MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
//
//        mediaSession.setMediaButtonReceiver(null);
//
//        playbackStateBuilder = new PlaybackStateCompat.Builder()
//                .setActions(
//                        PlaybackStateCompat.ACTION_PLAY |
//                                PlaybackStateCompat.ACTION_PAUSE |
//                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
//
//        mediaSession.setPlaybackState(playbackStateBuilder.build());
//
//        mediaSession.setCallback(new mediaSessionCallback());
//
//        mediaSession.setActive(true);
//    }

    private ExoPlayer.EventListener playerListener = new ExoPlayer.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object o) {}

        @Override
        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {}

        @Override
        public void onLoadingChanged(boolean b) {}

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//            if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
//                playbackStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
//                        mediaPlayer.getCurrentPosition(), 1f);
//            } else if((playbackState == ExoPlayer.STATE_READY)){
//                playbackStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
//                        mediaPlayer.getCurrentPosition(), 1f);
//            }
//            mediaSession.setPlaybackState(playbackStateBuilder.build());
//            showNotification(playbackStateBuilder.build());
        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {}

        @Override
        public void onPositionDiscontinuity() {}

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
    };

    public void setDatasource(Step datasource) {
        if (datasource == null) {
            stepDescription.setText(null);
            mediaSource.releaseSource();
            mediaPlayer.clearVideoSurface();
        } else {
            stepDescription.setText(datasource.getDescription());

            if (datasource.getVideoUrl() != null) {
                Uri mediaUri = Uri.parse(datasource.getVideoUrl());
                Log.i("STEP_VID_PLAYER", "Step media Uri: " + mediaUri.toString());

                mediaSource = buildMediaSource(mediaUri);
                initializePlayer();
            }
        }


    }

    private class mediaSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mediaPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mediaPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mediaPlayer.seekTo(0);
        }
    }

//    /**
//     * Broadcast Receiver registered to receive the MEDIA_BUTTON intent coming from clients.
//     */
//    public static class MediaReceiver extends BroadcastReceiver {
//
//        public MediaReceiver() {
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            MediaButtonReceiver.handleIntent(mMediaSession, intent);
//        }
//    }

}
