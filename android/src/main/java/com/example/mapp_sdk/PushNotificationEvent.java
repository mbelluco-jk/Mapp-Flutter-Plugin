package com.example.mapp_sdk;

import androidx.annotation.NonNull;

import com.appoxee.push.PushData;

import java.util.Map;

public class PushNotificationEvent implements Event {

    private static final String PUSH_RECEIVED_EVENT = "handledRemoteNotification";//"com.mapp.rich_message_received";

    private final PushData message;
    private final String type;

    public PushNotificationEvent(@NonNull PushData message, String type) {
        this.message = message;
        this.type = type;
    }

    @NonNull
    @Override
    public String getName() {
        return PUSH_RECEIVED_EVENT;
    }

    @NonNull
    @Override
    public Map<String, Object> getBody() {
        return MappSerializer.pushDataToMap(message, type);
    }
}