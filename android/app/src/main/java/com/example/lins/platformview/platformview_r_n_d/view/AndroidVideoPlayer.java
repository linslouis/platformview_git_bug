package com.example.lins.platformview.platformview_r_n_d.view;

import android.content.Context;
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

    public AndroidVideoPlayer(Context context, BinaryMessenger messenger, int viewId){


          videoView = new VideoView(context);
        MethodChannel methodChannel = new MethodChannel(messenger, "lins.platform.learn/VideoPlayer_"+viewId);
        methodChannel.setMethodCallHandler(this);

           Uri videoUri = Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4"); // Ensure the URL is correct and accessible
           videoView.setVideoURI(videoUri);
           videoView.setOnPreparedListener(mp -> videoView.start());




    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {

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


