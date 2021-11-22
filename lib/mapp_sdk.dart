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

  static Future<String?> optIn(bool optIn) async {
    final String? version = await _channel.invokeMethod('optIn', [optIn]);
    return 'successfull $version opted in device with value $optIn';
  }

  static Future<bool> isPushEnabled() async {
    final bool version = await _channel.invokeMethod('isPushEnabled');
    return version;
  }

  static Future<String?> logoutWithOptin(bool status) async {
    final String? version =
        await _channel.invokeMethod('logoutWithOptin', [status]);
    return 'successfull $version logged out with status $status';
  }

  static Future<String?> setDeviceAlias(String alias) async {
    final String? version =
        await _channel.invokeMethod('setDeviceAlias', [alias]);
    return 'successfull $version set new alias $alias';
  }

  static Future<String?> getDeviceAlias() async {
    final String alias = await _channel.invokeMethod('getDeviceAlias');
    return alias;
  }

  static Future<List<String>?> fetchDeviceTags() async {
    final List<String> tags = await _channel.invokeMethod('fetchDeviceTags');
    return tags;
  }

  static Future<List<String>?> fetchApplicationTags() async {
    final List<String> tags =
        await _channel.invokeMethod('fetchApplicationTags');
    return tags;
  }

  static Future<String?> addTagsToDevice(List<String> tags) async {
    final String version =
        await _channel.invokeMethod('addTagsToDevice', [tags]);
    return "Tags are added successfully! $version";
  }

  static Future<String?> removeTagsFromDevice(List<String> tags) async {
    final String version =
        await _channel.invokeMethod('removeTagsFromDevice', [tags]);
    return "Tags are removed successfully! $version";
  }

  static Future<String?> setDateValueWithKey(DateTime date, String key) async {
    final String version =
        await _channel.invokeMethod('setDateValueWithKey', [date, key]);
    return "Date $date added successfully! $version";
  }

  static Future<String?> setNumberValueWithKey(int number, String key) async {
    final String version =
        await _channel.invokeMethod('setNumberValueWithKey', [number, key]);
    return "Number $number added successfully! $version";
  }

  static Future<String?> incrementNumericKey(int number, String key) async {
    final String version =
        await _channel.invokeMethod('incrementNumericKey', [number, key]);
    return "Number $number incremented successfully! $version";
  }

  static Future<String?> setStringValueWithKey(String value, String key) async {
    final String version =
        await _channel.invokeMethod('setStringValueWithKey', [value, key]);
    return "String $value added successfully! $version";
  }

  static Future<String?> fetchCustomFieldByKey(String key) async {
    final String fieldValue =
        await _channel.invokeMethod('fetchCustomFieldByKey', [key]);
    return fieldValue;
  }

  static Future<String?> getDeviceInfo() async {
    final String deviceInfo = await _channel.invokeMethod('getDeviceInfo');
    return deviceInfo;
  }
}
