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
  static const platform = MethodChannel('com.example.lins.platformview/video');
  double heightValue = 300;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Column(
          children: [
            SizedBox(
              height: heightValue,
// Example of using a stable Key for AndroidView in Flutter
                child: const AndroidView(
                  key: GlobalObjectKey('video_player_key'), // Use a stable key
                  viewType: 'lins.platform.learn/VideoPlayer',
                ),

            ),
            ElevatedButton(
              onPressed: () async {
                setState(() {
                  heightValue += 50; // Increase height
                });
                await platform.invokeMethod('resize', {'height': heightValue});
              },
              child: const Text('Increase Height'),
            ),
          ],
        ),
        appBar: AppBar(
          title: const Text('Platform Surface Video Player'),
        ),
      ),
    );
  }
}
