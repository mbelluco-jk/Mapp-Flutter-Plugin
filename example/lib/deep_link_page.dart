import 'dart:collection';

import 'package:flutter/material.dart';

class DeepLinkPage extends StatelessWidget {
  const DeepLinkPage({Key? key, required this.map}) : super(key: key);

  final HashMap<String, dynamic> map;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Deep Link'),
      ),
      body: Center(
        child: ListView(
          children: [
            buildText('URL: ${map["url"]}'),
            buildText('Event trigger: ${map["event_trigger"]}'),
            buildText('Action: ${map["action"]}')
          ],
        ),
      ),
    );
  }

  Text buildText(String text) {
    return Text(text,
        textAlign: TextAlign.center,
        style: TextStyle(color: Colors.blueAccent));
  }
}
