import 'package:flutter/material.dart';

import 'android_video_player.dart';



void main() {
  runApp(const MyApp());
}
 class MyApp extends StatefulWidget {
   const MyApp({Key? key}) : super(key: key);

   @override
   State<MyApp> createState() => _MyAppState();
 }

 class _MyAppState extends State<MyApp> {
  double heightValue=200;



   @override
   Widget build(BuildContext context) {
     return MaterialApp(
         home: Scaffold(
            body: SingleChildScrollView(
             child: Column(
               children: [

                 SizedBox(

                   height: heightValue,

                   child:AndroidVideoPlayer(onAndroidVideoPlayerCreated: (AndroidVideoPlayerController controller) {
                     controller.setProgress(50);
                   },),
                   ),
                 ElevatedButton(  onPressed: () { setState(() {
                   heightValue=heightValue+10;
                 });},
                 child: const Text('Change Height')),
               ],
             ),
           ),appBar: AppBar(title: const Text('Platform view'),),));
   }
 }
