package com.example.mapp_sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;

import java.util.ArrayList;
import java.util.List;

import io.flutter.plugin.common.MethodChannel;

public class EventEmitter {
    private static volatile EventEmitter instance;

    private final List<Event> pendingIntents = new ArrayList<>();
    private final List<String> knownListeners = new ArrayList<>();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private MethodChannel channel;


    public static EventEmitter getInstance() {
        if (instance == null) {
            synchronized (EventEmitter.class) {
                if (instance == null) {
                    instance = new EventEmitter();
                }
            }
        }
        return instance;
    }

    public void attachChannel(MethodChannel channel) {
        this.channel = channel;
        synchronized (pendingIntents) {
            sendPendingIntents();
        }
    }

    /**
     * Sends an event to the Dart layer.
     *
     * @param event The event.
     */
    public synchronized void sendEvent(Event event) {
        mainHandler.post(() -> {
            if (!event.getBody().isEmpty() && !knownListeners.contains(event.getName())) {
                if (!emit(event)) {
                    pendingIntents.add(event);
                }
            }
        });
    }

    private void sendPendingIntents() {
        final List<Event> events = new ArrayList<>(pendingIntents);
        for (Event event : events) {
            if (knownListeners.contains(event.getName())) {
                pendingIntents.remove(event);
                sendEvent(event);
            }
        }
    }

    public synchronized void addAndroidListener(String eventName) {
        this.knownListeners.add(eventName);
        sendPendingIntents();
    }

    public void removeAndroidListeners() {
        synchronized (knownListeners) {
            knownListeners.clear();
        }
    }

    /**
     * Helper method to emit data.
     *
     * @param event The event.
     * @return {@code true} if the event was emitted, otherwise {@code false}.
     */
    @MainThread
    public boolean emit(Event event) {
        try {
            if (channel != null) {
                if (event != null)
                    channel.invokeMethod(event.getName(), event.getBody());
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public synchronized void detachChannel() {
        channel = null;
    }
}
