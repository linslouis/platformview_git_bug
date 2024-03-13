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
  bool isControllerInitialized = false;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Column(
          children: [
            SizedBox(
              height: heightValue,
              child: const AndroidSurfaceView(),
            ),
            ElevatedButton(
              onPressed: // Check if the controller is initialized
                  () async {
                setState(() {
                  heightValue += 50; // Increase height
                });
              },
              // Disable the button if the controller is not initialized
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
    return const AndroidView(
      viewType: 'lins.platform.learn/VideoPlayer',
    );
  }
}
