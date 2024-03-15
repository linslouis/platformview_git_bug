package com.example.lins.platformview.platformview_r_n_d.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

public class AndroidVideoPlayer implements PlatformView, MethodChannel.MethodCallHandler {

    private final VideoView videoView;
    private int currentPosition = 0;
    private boolean isPlaying = false;

    public AndroidVideoPlayer(Context context, BinaryMessenger messenger, int viewId) {

        videoView = new VideoView(context);
        MethodChannel methodChannel = new MethodChannel(messenger, "lins.platform.learn/VideoPlayer_" + viewId);
        methodChannel.setMethodCallHandler(this);

        Uri videoUri = Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4");
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(mp -> {
            if (!isPlaying) {
                videoView.start();
                videoView.seekTo(currentPosition);
            }
        });
        videoView.setOnCompletionListener(mp -> {
            videoView.seekTo(0);
            videoView.start();
        });


    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("pauseVideo")) {
            pauseVideo();
            result.success(null);
        } else if (call.method.equals("resumeVideo")) {
            resumeVideo();
            result.success(null);
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onInputConnectionLocked() {
        pauseVideo();
        PlatformView.super.onInputConnectionLocked();
    }

    @Override
    public void onInputConnectionUnlocked() {
        PlatformView.super.onInputConnectionUnlocked();
        resumeVideo();
    }

    private void pauseVideo() {
        if (videoView.isPlaying()) {
            videoView.pause();
            currentPosition = videoView.getCurrentPosition();
            isPlaying = false;
        }
    }

    private void resumeVideo() {
        if (!videoView.isPlaying()) {
            isPlaying = true;
            videoView.start();
            videoView.seekTo(currentPosition);
        }
    }

    @Nullable
    @Override
    public View getView() {
        return videoView;
    }

    @Override
    public void dispose() {
        videoView.stopPlayback();
    }
}
