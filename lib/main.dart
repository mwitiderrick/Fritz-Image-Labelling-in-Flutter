import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:async';

void main() {
  runApp(MaterialApp(
    theme: ThemeData(
      primaryColor: Colors.teal,
      fontFamily: 'Lato',
    ),
    home: Scaffold(
      appBar: AppBar(
        title: Text(
          'Label Image',
          style: TextStyle(
            color: Colors.teal,
          ),
        ),
        backgroundColor: Colors.white,
      ),
      body: NativeStuff(),
    ),
  ));
}

class NativeStuff extends StatefulWidget {
  @override
  NativeStuffState createState() {
    return NativeStuffState();
  }
}

class NativeStuffState extends State<NativeStuff> {
  static const platformMethodChannel =
      const MethodChannel('heartbeat.fritz.ai/native');
  String nativeMessage = '';

  Future<Null> _labelImage() async {
    String _message;
    try {
      final String result =
          await platformMethodChannel.invokeMethod('labelImage');
      _message = result;
    } on PlatformException catch (e) {
      _message = "Can't do native stuff ${e.message}.";
    }
    setState(() {
      nativeMessage = _message;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.teal,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          Container(
            margin: EdgeInsets.fromLTRB(0, 0, 0, 8),
            height: 300,
            child: Image.asset(
              'assets/bird.jpg',
              fit: BoxFit.cover,
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(left: 8.0, right: 8.0, top: 0.0),
            child: Center(
              child: FlatButton(
                  color: Colors.white,
                  onPressed: _labelImage,
                  child: Text(
                    "Label Image",
                  )),
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(left: 8.0, right: 8.0, top: 102.0),
            child: Center(
              child: Text(
                nativeMessage,
                style: TextStyle(
                    color: Colors.white,
                    fontWeight: FontWeight.w500,
                    fontSize: 23.0),
              ),
            ),
          )
        ],
      ),
    );
  }
}
