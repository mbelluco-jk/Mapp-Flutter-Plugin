import 'dart:async';

import 'package:flutter/services.dart';

import 'helper_classes.dart';

class MappSdk {
  // static const MethodChannel _channel = MethodChannel('mapp_sdk');
  // MappSdk.setMethodCallHandler(_platformCallHandler);

  static const MethodChannel _privateChannel = MethodChannel('mapp_sdk');

  static MethodChannel get _channel {
    _privateChannel.setMethodCallHandler(_platformCallHandler);
    return _privateChannel;
  }

  //inapp handlers
  static late void Function(dynamic) didReceiveDeepLinkWithIdentifier;
  static late void Function(dynamic) didReceiveInappMessageWithIdentifier;
  static late void Function(dynamic) didReceiveCustomLinkWithIdentifier;
  static late void Function(dynamic) didReceiveInBoxMessages;
  static late void Function(dynamic) inAppCallFailedWithResponse;
  static late void Function(dynamic) didReceiveInBoxMessage;

  //push notification handlers
  static late void Function(dynamic) handledRemoteNotification;
  static late void Function(dynamic) handledRichContent;

  //android push message handlers
  static late void Function(dynamic) handledPushOpen;
  static late void Function(dynamic) handledPushDismiss;
  static late void Function(dynamic) handledPushSilent;

  static Future<void> _platformCallHandler(MethodCall call) {
    try {
      print(call.method);

      switch (call.method) {
        case 'didReceiveDeepLinkWithIdentifier':
          didReceiveDeepLinkWithIdentifier(call.arguments);
          break;
        case 'didReceiveInappMessageWithIdentifier':
          didReceiveInappMessageWithIdentifier(call.arguments);
          break;
        case 'didReceiveCustomLinkWithIdentifier':
          didReceiveCustomLinkWithIdentifier(call.arguments);
          break;
        case 'didReceiveInBoxMessages':
          didReceiveInBoxMessages(call.arguments);
          break;
        case 'inAppCallFailedWithResponse':
          inAppCallFailedWithResponse(call.arguments);
          break;
        case 'didReceiveInBoxMessage':
          didReceiveInBoxMessage(call.arguments);
          break;
        case 'handledRemoteNotification':
          handledRemoteNotification(call.arguments);
          break;
        case 'handledRichContent':
          handledRichContent(call.arguments);
          break;
        case 'handledPushOpen':
          handledPushOpen(call.arguments);
          break;
        case 'handledPushDismiss':
          handledPushDismiss(call.arguments);
          break;
        case 'handledPushSilent':
          handledPushSilent(call.arguments);
          break;
        default:
          print('Unknowm method ${call.method} ');
      }
    } catch (e) {
      print(e);
    }
    return Future.value();
  }

  static Future<String> get platformVersion async {
    String? version = await _channel.invokeMethod('getPlatformVersion');
    return version ?? "unknown";
  }

  static Future<String?> engage(String sdkKey, String googleProjectId,
      SERVER server, String appID, String tenantID) async {
    final String? version = await _channel.invokeMethod(
        'engage', [sdkKey, googleProjectId, server.index, appID, tenantID]);
    return 'successfull $version engage method invoked';
  }

  static Future<String?> postponeNotificationRequest(bool postpone) async {
    final String? version =
        await _channel.invokeMethod('postponeNotificationRequest', [postpone]);
    return 'successfull $version postponeNotificationRequest method invoked';
  }

  static Future<String?> showNotificationsOnForeground(
      bool showNotification) async {
    final String? version = await _channel
        .invokeMethod('showNotificationsOnForeground', [showNotification]);
    return 'successfull $version postponeNotificationRequest method invoked';
  }

  static Future<bool> isReady() async {
    final bool version = await _channel.invokeMethod('isReady');
    return version;
  }

  static Future<String?> setPushEnabled(bool optIn) async {
    final bool isPushEnabled = await _channel.invokeMethod('optIn', [optIn]);
    return 'OptIn set to: $isPushEnabled opted in device with value $optIn';
  }

  static Future<bool> isPushEnabled() async {
    final bool version = await _channel.invokeMethod('isPushEnabled');
    return version;
  }

  static Future<String?> logOut(bool pushEnabled) async {
    final String? result =
        await _channel.invokeMethod('logoutWithOptin', [pushEnabled]);
    return result;
  }

  static Future<String?> setAlias(String alias) async {
    final String? version =
        await _channel.invokeMethod('setDeviceAlias', [alias]);
    return version;
  }

  static Future<String> getAlias() async {
    final String alias = await _channel.invokeMethod('getDeviceAlias');
    return alias;
  }

  static Future<Map<String, dynamic>?> getDeviceInfo() async {
    final Map<String, dynamic>? deviceInfo =
        await _channel.invokeMapMethod<String, dynamic>('getDeviceInfo');
    return deviceInfo;
  }

  static Future<String?> removeBadgeNumber() async {
    final String deviceInfo = await _channel.invokeMethod('removeBadgeNumber');
    return deviceInfo;
  }

  //InApp methods
  static Future<String?> triggerInApp(String value) async {
    final String returnValue =
        await _channel.invokeMethod('triggerInApp', [value]);
    return returnValue;
  }

  static Future<dynamic> fetchInboxMessage() async {
    final String returnValue = await _channel.invokeMethod('fetchInboxMessage');
    return returnValue;
  }

  static Future<String?> fetchInBoxMessageWithMessageId(int value) async {
    final String returnValue =
        await _channel.invokeMethod('fetchInBoxMessageWithMessageId', [value]);
    return returnValue;
  }
}
