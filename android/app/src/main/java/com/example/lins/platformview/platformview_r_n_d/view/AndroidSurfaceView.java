package com.example.lins.platformview.platformview_r_n_d.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;

public class AndroidSurfaceView implements PlatformView, SurfaceHolder.Callback {
    private final SurfaceView surfaceView;
    private final MediaPlayer mediaPlayer;
    private final Context context;

    public AndroidSurfaceView(Context context, BinaryMessenger viewId, Object args) {
        this.context = context;
        surfaceView = new SurfaceView(context);
        surfaceView.getHolder().addCallback(this);

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context, Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
            mediaPlayer.setLooping(true); // Optional: For continuous playback
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // This method is called when the surface size is changed.
        // No action is needed here for video playback, but you might want to handle aspect ratio adjustments.
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // Handle surface destruction here if necessary.
        // For instance, you might want to pause the video here.
    }

    @Nullable
    @Override
    public View getView() {
        return surfaceView;
    }

    @Override
    public void dispose() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }
}
