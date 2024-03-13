package com.example.lins.platformview.platformview_r_n_d.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;

import io.flutter.Log;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;

public class AndroidSurfaceView implements PlatformView {

    private final SurfaceView surfaceView;
    private Bitmap imageBitmap = null;

    public AndroidSurfaceView(Context context, BinaryMessenger messenger, int viewId) {
        surfaceView = new SurfaceView(context);


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                // Load the image from assets on a separate thread
                new Thread(() -> {
                    try {
                        InputStream inputStream = context.getAssets().open("cat.jpg");
                        imageBitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                        draw();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                draw();
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                // Handle surface destruction if necessary
            }
        });
    }

    private void draw() {
        if (imageBitmap != null) {
            Canvas canvas = surfaceView.getHolder().lockCanvas();
            if (canvas != null) {
                // Center the image in the SurfaceView
                int canvasWidth = canvas.getWidth();
                int canvasHeight = canvas.getHeight();
                int imageWidth = imageBitmap.getWidth();
                int imageHeight = imageBitmap.getHeight();
                float scale = Math.min((float) canvasWidth / imageWidth, (float) canvasHeight / imageHeight);

                float x = (canvasWidth - imageWidth * scale) / 2;
                float y = (canvasHeight - imageHeight * scale) / 2;

                canvas.save();
                canvas.scale(scale, scale);
                canvas.drawBitmap(imageBitmap, x / scale, y / scale, null);
                canvas.restore();

                surfaceView.getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

    @Nullable
    @Override
    public View getView() {
        return surfaceView;
    }

    @Override
    public void dispose() {
        // Clean up any resources here if necessary
    }

    @Override
    public void onInputConnectionLocked() {
        PlatformView.super.onInputConnectionLocked();
        Log.d("AndroidSurfaceView", "onInputConnectionLocked");
    }

    @Override
    public void onInputConnectionUnlocked() {
        PlatformView.super.onInputConnectionUnlocked();
        Log.d("AndroidSurfaceView", "onInputConnectionUnlocked");
    }
}
