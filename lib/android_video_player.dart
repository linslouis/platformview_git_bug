import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

typedef AndroidVideoPlayerCreatedCallback = void Function(AndroidVideoPlayerController controller);

class AndroidVideoPlayer extends StatefulWidget {
  const AndroidVideoPlayer({Key? key, required this.onAndroidVideoPlayerCreated}) : super(key: key);
  final AndroidVideoPlayerCreatedCallback onAndroidVideoPlayerCreated;
  @override
  State<AndroidVideoPlayer> createState() => _AndroidVideoPlayerState();
}

class _AndroidVideoPlayerState extends State<AndroidVideoPlayer> {
  @override
  Widget build(BuildContext context) {
    return AndroidView(viewType: 'lins.platform.learn/VideoPlayer',
    onPlatformViewCreated: _onPlatformViewCreated,);
  }

  void _onPlatformViewCreated(int id) {
    if (widget.onAndroidVideoPlayerCreated== null) {
      return;
    }
    widget.onAndroidVideoPlayerCreated(AndroidVideoPlayerController._(id));
  }
}

class AndroidVideoPlayerController {
  AndroidVideoPlayerController._(int id)
      : _channel = MethodChannel('lins.platform.learn/VideoPlayer_$id');

  final MethodChannel _channel;

  Future<void> setProgress(int porgress) async {

    return _channel.invokeMethod('setprogress', porgress);
  }
}