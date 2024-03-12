import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  double heightValue = 300; // Initial height of the video player
  AndroidVideoPlayerController? videoController; // Make it nullable
  bool isControllerInitialized = false; // Track if the controller is initialized

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: SingleChildScrollView(
          child: Column(
            children: [
              SizedBox(
                height: heightValue,
                child: AndroidVideoPlayer(
                  onAndroidVideoPlayerCreated: (controller) {
                    setState(() {
                      videoController = controller;
                      isControllerInitialized = true; // Mark it as initialized
                    });
                  },
                ),
              ),
              ElevatedButton(
                onPressed: isControllerInitialized // Check if the controller is initialized
                    ? () async {
                  await videoController?.pauseVideo();
                  setState(() {
                    heightValue += 50; // Increase height
                  });
                  await videoController?.resumeVideo();
                }
                    : null, // Disable the button if the controller is not initialized
                child: const Text('Increase Height'),
              ),
              ElevatedButton(
                onPressed: isControllerInitialized // Check if the controller is initialized
                    ? () async {
                  await videoController?.pauseVideo();
                  setState(() {
                    heightValue -= 50; // Decrease height
                  });
                  await videoController?.resumeVideo();
                }
                    : null, // Disable the button if the controller is not initialized
                child: const Text('Decrease Height'),
              ),
            ],
          ),
        ),
        appBar: AppBar(
          title: const Text('Platform View Video Player'),
        ),
      ),
    );
  }
}

class AndroidVideoPlayer extends StatefulWidget {
  final Function(AndroidVideoPlayerController) onAndroidVideoPlayerCreated;

  const AndroidVideoPlayer({Key? key, required this.onAndroidVideoPlayerCreated}) : super(key: key);

  @override
  State<AndroidVideoPlayer> createState() => _AndroidVideoPlayerState();
}

class _AndroidVideoPlayerState extends State<AndroidVideoPlayer> {
  @override
  Widget build(BuildContext context) {
    return AndroidView(
      viewType: 'lins.platform.learn/VideoPlayer',
      onPlatformViewCreated: _onPlatformViewCreated,
    );
  }

  void _onPlatformViewCreated(int id) {
    widget.onAndroidVideoPlayerCreated(AndroidVideoPlayerController._(id));
  }
}

class AndroidVideoPlayerController {
  final MethodChannel _channel;

  AndroidVideoPlayerController._(int id)
      : _channel = MethodChannel('lins.platform.learn/VideoPlayer_$id');

  Future<void> pauseVideo() async {
    await _channel.invokeMethod('pauseVideo');
  }

  Future<void> resumeVideo() async {
    await _channel.invokeMethod('resumeVideo');
  }
}
