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
  double heightValue = 300;

  // Add a key based on the heightValue to force widget recreation on size change.
  ValueKey heightKey = ValueKey(300);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Column(
          children: [
            SizedBox(
              height: heightValue,
              // Pass the dynamic key to the AndroidSurfaceView
              child: AndroidSurfaceView(key: heightKey),
            ),
            ElevatedButton(
              onPressed: () async {
                setState(() {
                  heightValue += 50; // Increase height
                  // Update the key to a new one, based on the new heightValue
                  heightKey = ValueKey(heightValue);
                });
              },
              child: const Text('Increase Height'),
            ),
          ],
        ),
        appBar: AppBar(
          title: const Text('Platform Surface Bug'),
        ),
      ),
    );
  }
}

class AndroidSurfaceView extends StatefulWidget {
  const AndroidSurfaceView({Key? key}) : super(key: key);

  @override
  State<AndroidSurfaceView> createState() => _AndroidSurfaceViewState();
}

class _AndroidSurfaceViewState extends State<AndroidSurfaceView> {
  @override
  Widget build(BuildContext context) {
    return AndroidView(
      viewType: 'lins.platform.learn/VideoPlayer',
      // Additional AndroidView parameters as needed, e.g., creationParams
    );
  }
}
