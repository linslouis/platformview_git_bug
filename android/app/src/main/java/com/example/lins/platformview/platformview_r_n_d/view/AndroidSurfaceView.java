package com.example.lins.platformview.platformview_r_n_d.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lins.platformview.platformview_r_n_d.R;

import java.io.IOException;
import io.flutter.Log;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;

public class AndroidSurfaceView implements PlatformView, SurfaceHolder.Callback {
    private static final String TAG = "VideoSurfaceViewPluginTest";
    private final Context context;
    private LinearLayout rootView;
    private SurfaceView surfaceView;
    private final MediaPlayer mediaPlayer;

    public AndroidSurfaceView(Context context, BinaryMessenger viewId, Object args) {
        this.context = context;
        mediaPlayer = new MediaPlayer();
        initializeMediaPlayer();
        initializeView();
    }

    private void initializeMediaPlayer() {
        try {
            mediaPlayer.setDataSource(context, Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeView() {
        rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout, null);
        surfaceView = rootView.findViewById( R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);
    }

    @Nullable
    @Override
    public View getView() {
        return rootView;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mediaPlayer.setDisplay(holder);
        Log.d(TAG, "Surface created");
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "Surface changed to format: " + format + " width: " + width + " height: " + height);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d(TAG, "Surface destroyed");
    }

    @Override
    public void dispose() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }
}
