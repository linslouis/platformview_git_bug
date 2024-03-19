package com.example.lins.platformview.platformview_r_n_d;

import androidx.annotation.NonNull;
import com.example.lins.platformview.platformview_r_n_d.plugin.SurfaceViewPlugin;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

    private static final String CHANNEL = "com.example.lins.platformview/video";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        flutterEngine.getPlugins().add(new SurfaceViewPlugin());

        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler(
                        (call, result) -> {
                            if (call.method.equals("resize")) {
                                Number height = call.argument("height");
                                if (height != null) {
                                    // Here we handle the height as a Number, which can then be converted to any numeric type
                                    int heightInt = height.intValue(); // Convert the number to an integer
                                    // Add your resizing logic here with heightInt as the height
                                    System.out.println("Resizing SurfaceView to height: " + heightInt);

                                    result.success(null); // Acknowledge the Flutter side
                                } else {
                                    result.error("INVALID_ARGUMENT", "Argument 'height' is null or missing", null);
                                }
                            } else {
                                result.notImplemented();
                            }
                        }
                );
    }

}
