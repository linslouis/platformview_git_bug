package com.example.lins.platformview.platformview_r_n_d;

import androidx.annotation.NonNull;


import com.example.lins.platformview.platformview_r_n_d.plugin.VideoPlayerPlugin;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);

       flutterEngine.getPlugins().add(new VideoPlayerPlugin());


    }
}
