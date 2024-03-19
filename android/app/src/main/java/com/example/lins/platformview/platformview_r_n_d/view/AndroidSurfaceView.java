package com.example.lins.platformview.platformview_r_n_d.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.media.ImageReader;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;

public class AndroidSurfaceView implements PlatformView, SurfaceHolder.Callback {

    private static final String TAG = "========linslog======";
    private final SurfaceView surfaceView;
    private Bitmap imageBitmap = null;
    private ImageReader imageReader;
    private final Context context;

    public AndroidSurfaceView(Context context, BinaryMessenger messenger, int viewId) {
        Log.d(TAG, "AndroidSurfaceView constructor called");
        this.context = context;
        this.surfaceView = new SurfaceView(context);
        this.surfaceView.getHolder().addCallback(this);
        initializeResources();
    }

    private void initializeResources() {
        // Initialize your ImageReader or other resources here
        // Assuming you have some dimensions to start with
        imageReader = ImageReader.newInstance(1440, 1225, ImageFormat.YUV_420_888, 2);
        loadImage();
    }

    private void loadImage() {
        new Thread(() -> {
            try {
                InputStream inputStream = context.getAssets().open("cat.jpg");
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                imageBitmap = bitmap;
                Log.d(TAG, "Image loaded");
            } catch (Exception e) {
                Log.e(TAG, "Exception loading image", e);
            }
        }).start();
    }

    private void draw() {
        if (imageBitmap != null && !imageBitmap.isRecycled()) {
            Canvas canvas = surfaceView.getHolder().lockCanvas();
            if (canvas != null) {
                try {
                    // Drawing code remains unchanged
                    canvas.drawBitmap(imageBitmap, 0, 0, null);
                } finally {

                    surfaceView.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    // SurfaceHolder.Callback methods
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d(TAG, "Surface created");
        draw(); // Draw when the surface is created
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "Surface changed");
        // Adjust ImageReader size or any other resource based on new surface dimensions
        if (imageReader != null) {
            imageReader.close();
        }
        imageReader = ImageReader.newInstance(width, height, ImageFormat.YUV_420_888, 2);
        // Optionally, redraw or perform other actions as needed
        draw();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d(TAG, "Surface destroyed");
        if (imageReader != null) {
            imageReader.close(); // Properly close and release resources
            imageReader = null;
        }
    }

    @Nullable
    @Override
    public View getView() {
        return surfaceView;
    }

    @Override
    public void dispose() {
        // Clean up resources
        Log.d(TAG, "Surface disposed");

        if (imageBitmap != null) {
            imageBitmap.recycle();
            imageBitmap = null;
        }
        if (imageReader != null) {
            imageReader.close();
            imageReader = null;
        }
    }
}
