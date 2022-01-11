package com.example.mapp_sdk;

import android.content.Context;
import android.content.Intent;

import com.appoxee.internal.logger.LoggerFactory;
import com.appoxee.push.PushData;
import com.appoxee.push.PushDataReceiver;

import java.util.Map;

public class PushBroadcastReceiver extends PushDataReceiver {
    private final EventEmitter eventEmitter=EventEmitter.getInstance();

    @Override
    public void onPushReceived(PushData pushData) {
        super.onPushReceived(pushData);
        LoggerFactory.getDevLogger().d("onPushReceived!!!");

        //eventEmitter.sendEvent(new PushNotificationEvent(pushData,"onPushReceived"));
        eventEmitter.sendEvent(new PushNotificationEvent(pushData,"handledRemoteNotification"));

        //eventEmitter.sendEvent(new PushDeepLinkEvent(pushData,"didReceiveDeepLinkWithIdentifier"));
    }

    @Override
    public void onPushOpened(PushData pushData) {
        super.onPushOpened(pushData);
        eventEmitter.sendEvent(new PushNotificationEvent(pushData,"onPushOpened"));
    }

    @Override
    public void onPushDismissed(PushData pushData) {
        super.onPushDismissed(pushData);
        eventEmitter.sendEvent(new PushNotificationEvent(pushData, "onPushDismissed"));
    }

    @Override
    public void onSilentPush(PushData pushData) {
        super.onSilentPush(pushData);
        eventEmitter.sendEvent(new PushNotificationEvent(pushData, "onSilentPush"));
    }
}
