package com.example.lins.platformview.platformview_r_n_d.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.lins.platformview.platformview_r_n_d.view.AndroidSurfaceView;


import io.flutter.plugin.common.BinaryMessenger;

import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class SurfaceVIewFactory extends PlatformViewFactory {
    /**
     * @param createArgsCodec the codec used to decode the args parameter of {@link #create}.
     */
    private BinaryMessenger messenger;
    public SurfaceVIewFactory(BinaryMessenger messenger) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger=messenger;    }

    @NonNull
    @Override
    public PlatformView create(@Nullable Context context, int viewId, @Nullable Object args) {
        return new AndroidSurfaceView(context,messenger,viewId);
    }
}
