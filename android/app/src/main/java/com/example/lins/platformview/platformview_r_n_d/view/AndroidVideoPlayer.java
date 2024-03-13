package com.example.lins.platformview.platformview_r_n_d.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

import java.io.IOException;
import java.io.InputStream;

public class AndroidVideoPlayer implements PlatformView, MethodChannel.MethodCallHandler {

    private final ImageView imageView;

    public AndroidVideoPlayer(Context context, BinaryMessenger messenger, int viewId) {
        imageView = new ImageView(context);
        MethodChannel methodChannel = new MethodChannel(messenger, "lins.platform.learn/VideoPlayer_" + viewId);
        methodChannel.setMethodCallHandler(this);

        try {
            // Load the image from assets
            InputStream inputStream = context.getAssets().open("cat.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        // Implement any method calls if necessary
        result.notImplemented();
    }

    @Nullable
    @Override
    public View getView() {
        return imageView;
    }

    @Override
    public void dispose() {
        // Clean up any resources here if necessary
    }
}
