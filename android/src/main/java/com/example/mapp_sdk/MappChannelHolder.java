package com.example.mapp_sdk;

import android.content.Context;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodChannel;

public class MappChannelHolder {

    public static final String MAPP_CHANNEL_NAME="mapp_sdk";

    private static volatile MethodChannel methodChannel;

    public static MethodChannel createAndGetChannel(Context context) {
        if (methodChannel == null) {
            synchronized (MappChannelHolder.class) {
                if (methodChannel == null) {
                    methodChannel = createMethodChannel(context);
                }
            }
        }
        return methodChannel;
    }

    public static synchronized MethodChannel getMethodChannel(){
        return methodChannel;
    }

    public static void setChannel(MethodChannel channel) {
        methodChannel = channel;
    }

    private static MethodChannel createMethodChannel(Context context) {
        MethodChannel channel;

        FlutterEngine flutterEngine = new FlutterEngine(context, null);
        DartExecutor dartExecutor = flutterEngine.getDartExecutor();
        channel = new MethodChannel(dartExecutor, MAPP_CHANNEL_NAME);
        DartExecutor.DartEntrypoint dartEntrypoint = DartExecutor.DartEntrypoint.createDefault();
        flutterEngine.getDartExecutor().executeDartEntrypoint(dartEntrypoint);

        return channel;
    }
}
