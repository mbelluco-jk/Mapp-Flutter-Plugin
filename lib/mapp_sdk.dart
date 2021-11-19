import 'dart:async';

import 'package:flutter/services.dart';
import 'helper_classes.dart';

class MappSdk {
  static const MethodChannel _channel = MethodChannel('mapp_sdk');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String?> engage(SERVER server) async {
    final String? version =
        await _channel.invokeMethod('engage', [server.index]);
    return 'successfull $version engage method invoked';
  }
}
