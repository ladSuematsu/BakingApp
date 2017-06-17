package com.ladsoft.bakingapp.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.ladsoft.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepFragment extends Fragment {

    private static final String TAG = RecipeStepFragment.class.getSimpleName();

    @BindView(R.id.step_description) TextView stepDescription;
    @BindView(R.id.media_player) SimpleExoPlayerView mediaPlayerView;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        initializeMediaSession();
//        setDataSource();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Util.SDK_INT <= 23) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    public void setDataSource() {
//        initializePlayer();
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
        }

        MediaSource mediaSource = buildMediaSource(Uri.parse(getString(R.string.media_url_dash)));
        mediaPlayer.prepare(mediaSource, true, false);

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
        DashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(dataSourceFactory);
        return new DashMediaSource(uri, dataSourceFactory, dashChunkSourceFactory, null, null);
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
