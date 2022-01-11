package com.example.mapp_sdk;

import androidx.annotation.NonNull;

import com.appoxee.push.PushData;

import java.util.HashMap;
import java.util.Map;

public class PushDeepLinkEvent implements Event{

    private static final String DEEP_LINK_RECEIVED_EVENT = "didReceiveDeepLinkWithIdentifier";//"com.mapp.deep_link_received";

    private PushData pushData;
    private String type;

    public PushDeepLinkEvent(PushData pushData, String type) {
        this.pushData = pushData;
        this.type = type;
    }

    @NonNull
    @Override
    public String getName() {
        return DEEP_LINK_RECEIVED_EVENT;
    }

    @NonNull
    @Override
    public Map<String, Object> getBody() {
        Map<String, Object> deepLink = new HashMap<>();
        String url = pushData.extraFields.containsKey("apx_dpl") ? pushData.extraFields.get("apx_dpl") : null;
        if(url!=null && !url.isEmpty()) {
            deepLink.put("action", pushData.id);
            deepLink.put("url", url);
            deepLink.put("event_trigger", "");
        }
        return deepLink;
    }
}
