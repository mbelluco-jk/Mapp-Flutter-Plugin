import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:mapp_sdk/mapp_sdk.dart';
import 'package:mapp_sdk/helper_classes.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
          primaryColor: const Color(0xFF00BAFF),
          primaryColorDark: const Color(0xFF0592D7),
          accentColor: const Color(0xFF58585A),
          cardColor: const Color(0xFF888888)),
      home: HomePage(),
    );
  }
}

// ignore: use_key_in_widget_constructors
class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  String? _platformVersion = 'Unknown';
  String? _aliasToSetString = '';
  List<String> _screens = [];

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String? platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await MappSdk.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
      _screens = [
        "Set Device Alias Text",
        "Set Device Alias",
        "Get Device Alias",
        "Device Information",
        "Is Push Enabled",
        "Opt in",
        "Opt out",
        "Start Geo",
        "Stop Geo",
        "Fetch inbox messages",
        "In App: App Open",
        "In App: App Feedback",
        "In App: App Discount",
        "In App: App Promo",
        "Get Tags",
        "Set Tag Text",
        "Set Tags",
        "Remove Tag Text",
        "Remove Tag",
        "Set Attribute Text",
        "Set Attribute",
        "Get Attribute Text",
        "Get Attribute",
        "Remove Attribute Text",
        "Remove Attribute",
        "Remove Badge Number",
        "Lock Orientation",
        "Engage",
        "Log out"
      ];
    });
  }

  Card _createTextFieldOrButton(int index) {
    switch (index) {
      case 0:
        return Card(
          child: TextFormField(
            decoration: const InputDecoration(
                border: UnderlineInputBorder(), labelText: 'Enter alias'),
            onSaved: (String? value) {
              _aliasToSetString = value;
            },
          ),
        );
      case 15:
        return Card(
          child: TextFormField(
            decoration: const InputDecoration(
                border: UnderlineInputBorder(), labelText: 'Set tag'),
          ),
        );
      case 17:
        return Card(
          child: TextFormField(
            decoration: const InputDecoration(
                border: UnderlineInputBorder(), labelText: 'Remove tag'),
          ),
        );
      case 19:
        return Card(
          child: TextFormField(
            decoration: const InputDecoration(
                border: UnderlineInputBorder(), labelText: 'Set Attribute'),
          ),
        );
      case 21:
        return Card(
          child: TextFormField(
            decoration: const InputDecoration(
                border: UnderlineInputBorder(), labelText: 'Get attribute'),
          ),
        );
      case 23:
        return Card(
          child: TextFormField(
            decoration: const InputDecoration(
                border: UnderlineInputBorder(), labelText: 'Remove attribute'),
          ),
        );
      default:
        return Card(
          child: ListTile(
            title: Text(
              _screens[index],
              style: TextStyle(color: Theme.of(context).primaryColor),
              textAlign: TextAlign.center,
            ),
            onTap: () {
              onTap(index);
            },
          ),
        );
    }
  }

  void onTap(int index) {
    if (_screens[index] == "Engage") {
      MappSdk.engage(
          "sdk key", "google projec id", SERVER.TEST, "app id", "tennant id");
    }
  }

  ListView _buildListView(BuildContext context) {
    return ListView.builder(
      itemCount: _screens.length,
      itemBuilder: (context, index) {
        return _createTextFieldOrButton(index);
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('Mapp SDK Demo'),
            backgroundColor: Theme.of(context).primaryColorDark,
          ),
          body: _buildListView(context)),
    );
  }
}
