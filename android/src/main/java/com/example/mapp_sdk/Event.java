package com.example.mapp_sdk;

import androidx.annotation.NonNull;

import java.util.Map;

public interface Event {

    /**
     * The event name.
     * @return The event name.
     */
    String getName();

    /**
     * The event body.
     * @return The event body.
     */
    Map<String, Object> getBody();
}