package com.example.mapp_sdk;

import com.appoxee.DeviceInfo;
import com.appoxee.push.PushData;

import java.util.HashMap;
import java.util.Map;

public class MappSerializer {
    public static Map<String, Object> deviceInfoToMap(DeviceInfo deviceInfo) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("id", deviceInfo.id);
            map.put("appVersion", deviceInfo.appVersion);
            map.put("sdkVersion", deviceInfo.sdkVersion);
            map.put("locale", deviceInfo.locale);
            map.put("timezone", deviceInfo.timezone);
            map.put("deviceModel", deviceInfo.deviceModel);
            map.put("manufacturer", deviceInfo.manufacturer);
            map.put("osVersion", deviceInfo.osVersion);
            map.put("resolution", deviceInfo.resolution);
            map.put("density", String.valueOf(deviceInfo.density));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public static Map<String, Object> pushDataToMap(PushData data, String type) {
        Map<String, Object> map = new HashMap<>();

        try {
            map.put("id", data.id);
            map.put("title", data.title);
            map.put("bigText", data.bigText);
            map.put("sound", data.sound);
            map.put("pushNotificationEventType", type);
            if (data.actionUri != null)
                map.put("actionUri", data.actionUri.toString());
            map.put("collapseKey", data.collapseKey);
            map.put("badgeNumber", data.badgeNumber);
            map.put("silentType", data.silentType);
            map.put("silentData", data.silentData);
            map.put("category", data.category);
            if (data.extraFields != null)
                for (Map.Entry<String, String> entry : data.extraFields.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    map.put(key, value);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
