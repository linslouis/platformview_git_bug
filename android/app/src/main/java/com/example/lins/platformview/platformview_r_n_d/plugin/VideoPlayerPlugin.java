package com.example.lins.platformview.platformview_r_n_d.plugin;

import androidx.annotation.NonNull;

import com.example.lins.platformview.platformview_r_n_d.factory.VideoPlayerFactory;

import io.flutter.embedding.engine.plugins.FlutterPlugin;

public class VideoPlayerPlugin implements FlutterPlugin {
    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {

        binding.getPlatformViewRegistry().registerViewFactory("lins.platform.learn/VideoPlayer",new VideoPlayerFactory(binding.getBinaryMessenger()));
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {


    }
}
