package com.example.mapp_sdk;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

import com.appoxee.Appoxee;
import com.appoxee.AppoxeeOptions;
import com.appoxee.DeviceInfo;
import com.appoxee.RequestStatus;
import com.appoxee.internal.logger.Logger;
import com.appoxee.internal.logger.LoggerFactory;
import com.appoxee.push.NotificationMode;

import java.util.List;
import java.util.Set;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.broadcastreceiver.BroadcastReceiverAware;
import io.flutter.embedding.engine.plugins.broadcastreceiver.BroadcastReceiverPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * MappSdkPlugin
 */
public class MappSdkPlugin implements FlutterPlugin, ActivityAware, MethodCallHandler {

    public static final String MAPP_CHANNEL_NAME = "mapp_sdk";

    public static final String GET_PLATFORM_VERSION = "getPlatformVersion";
    public static final String ENGAGE = "engage";
    public static final String SET_DEVICE_ALIAS = "setDeviceAlias";
    public static final String GET_DEVICE_ALIAS = "getDeviceAlias";
    public static final String IS_PUSH_ENABLED = "isPushEnabled";
    public static final String OPT_IN = "optIn";
    public static final String TRIGGER_IN_APP = "triggerInApp";
    public static final String IS_READY = "isReady";
    public static final String GET_DEVICE_INFO = "getDeviceInfo";
    public static final String FETCH_INBOX_MESSAGE = "fetchInboxMessage";
    public static final String FETCH_INBOX_MESSAGES = "fetchInboxMessages";
    public static final String GET_FCM_TOKEN = "getFcmToken";
    public static final String SET_TOKEN = "setToken";
    public static final String START_GEOFENCING = "startGeofencing";
    public static final String STOP_GEOFENCING = "stopGeofencing";
    public static final String ADD_TAG = "addTag";
    public static final String FETCH_DEVICE_TAGS = "fetchDeviceTags";
    public static final String LOGOUT_WITH_OPT_IN = "logoutWithOptin";
    public static final String IS_DEVICE_REGISTERED = "isDeviceRegistered";
    public static final String SET_REMOTE_MESSAGE = "setRemoteMessage";

    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private Application application;
    private Activity activity;
    private Result result;

    private final Logger devLogger = LoggerFactory.getDevLogger();

    private final Appoxee.OnInitCompletedListener onInitCompletedListener = new Appoxee.OnInitCompletedListener() {
        @Override
        public void onInitCompleted(boolean successful, Exception failReason) {
            if (result != null) {
                if (successful) {
                    Appoxee.instance().getDeviceInfoDMC();
                    DeviceInfo info = Appoxee.instance().getDeviceInfo();
                    devLogger.d("DEVICE INFO: " + info);
                    result.success(info.sdkVersion);
                }
                if (failReason != null) {
                    result.error("engage", failReason.getMessage(), null);
                }
            }
        }
    };

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        devLogger.d("attached to engine");
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), MAPP_CHANNEL_NAME);
        application = (Application) flutterPluginBinding.getApplicationContext();

        EventEmitter.getInstance().attachChannel(channel);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        List<Object> args = call.arguments();
        devLogger.d("method: " + call.method);
        switch (call.method) {
            case GET_PLATFORM_VERSION:
                result.success("Android " + android.os.Build.VERSION.RELEASE);
                break;
            case ENGAGE:
                engage(args, result);
                break;
            case SET_DEVICE_ALIAS:
                setDeviceAlias((String) args.get(0), result);
                break;
            case GET_DEVICE_ALIAS:
                getDeviceAlias(result);
                break;
            case IS_PUSH_ENABLED:
                isPushEnabled(result);
                break;
            case OPT_IN:
                boolean isEnabled = (boolean) args.get(0);
                setPushEnabled(isEnabled, result);
                break;
            case TRIGGER_IN_APP:
                triggerInApp((String) args.get(0), result);
                break;
            case IS_READY:
                isReady(result);
                break;
            case GET_DEVICE_INFO:
                getDeviceInfo(result);
                break;
            case FETCH_INBOX_MESSAGE:
                int templateId = args != null && args.size() > 0 ? (Integer) args.get(0) : -1;
                fetchInboxMessage(templateId, result);
                break;
            case FETCH_INBOX_MESSAGES:
                fetchInboxMessages(result);
                break;
            case GET_FCM_TOKEN:
                getFcmToken(result);
                break;
            case SET_TOKEN:
                setToken((String) args.get(0), result);
                break;
            case START_GEOFENCING:
                startGeoFencing(result);
                break;
            case STOP_GEOFENCING:
                stopGeoFencing(result);
                break;
            case ADD_TAG:
                addTag((String) args.get(0), result);
                break;
            case FETCH_DEVICE_TAGS:
                getTags(result);
                break;
            case LOGOUT_WITH_OPT_IN:
                logOut((Boolean) args.get(0), result);
                break;
            case IS_DEVICE_REGISTERED:
                isDeviceRegistered(result);
                break;
            case SET_REMOTE_MESSAGE:
                // TODO get remoteMessage and pass data to a native part
                // setRemoteMessage(null,result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        devLogger.d("detached from engine");
        channel.setMethodCallHandler(null);
        EventEmitter.getInstance().detachChannel();
        application = null;
    }

    private void engage(List<Object> args, @NonNull Result result) {
        try {
            //[sdkKey, googleProjectId, server.index, appID, tenantID]
            AppoxeeOptions options = new AppoxeeOptions();
            options.sdkKey = (String) args.get(0);
            options.googleProjectId = (String) args.get(1);
            options.server = getServerByIndex((Integer) args.get(2));
            options.appID = (String) args.get(3);
            options.tenantID = (String) args.get(4);
            options.notificationMode = NotificationMode.BACKGROUND_AND_FOREGROUND;
            Appoxee.engage(application, options);
            this.result = result;
            Appoxee.instance().addInitListener(onInitCompletedListener);
            Appoxee.instance().setReceiver(PushBroadcastReceiver.class);
        } catch (Exception e) {
            result.error("engage", e.getMessage(), null);
        }
    }

    private void setDeviceAlias(String alias, @NonNull Result result) {
        RequestStatus status = Appoxee.instance().setAlias(alias);
        result.success(alias);
    }

    private void getDeviceAlias(@NonNull Result result) {
        try {
            String alias = Appoxee.instance().getAlias();
            result.success(alias);
        } catch (Exception e) {
            result.error("getDeviceAlias", e.getMessage(), null);
        }
    }

    private void isPushEnabled(@NonNull Result result) {
        try {
            boolean isPushEnabled = Appoxee.instance().isPushEnabled();
            result.success(isPushEnabled);
        } catch (Exception e) {
            result.error("isPushEnabled", e.getMessage(), null);
        }
    }

    private void setPushEnabled(boolean pushEnabled, @NonNull Result result) {
        try {
            RequestStatus status = Appoxee.instance().setPushEnabled(pushEnabled);
            if (status == RequestStatus.SUCCESS) {
                result.success(true);
            } else {
                result.error("setPushEnabled", "Error getting push enabled state", null);
            }
        } catch (Exception e) {
            result.error("setPushEnabled", e.getMessage(), null);
        }
    }

    private void triggerInApp(String event, @NonNull Result result) {
        try {
            Appoxee.instance().triggerInApp(activity, event);
            result.success("");
        } catch (Exception e) {
            result.error("isReady", e.getMessage(), null);
        }
    }

    private void isReady(@NonNull Result result) {
        try {
            boolean isReady = Appoxee.instance().isReady();
            result.success(isReady);
        } catch (Exception e) {
            result.error("isReady", e.getMessage(), null);
        }
    }

    private void getDeviceInfo(@NonNull Result result) {
        try {
            DeviceInfo deviceInfo = Appoxee.instance().getDeviceInfo();
            if (deviceInfo != null) {
                result.success(MappSerializer.deviceInfoToMap(deviceInfo));
            } else {
                result.error("getDeviceInfo", "Can't get device info!", null);
            }
        } catch (Exception e) {
            result.error("getDeviceInfo", e.getMessage(), null);
        }
    }

    private void fetchInboxMessage(int template, @NonNull Result result) {
        try {
            Appoxee.instance().fetchInboxMessage(activity, template);
        } catch (Exception e) {
            result.error("fetchInboxMessage", e.getMessage(), null);
        }
    }

    private void fetchInboxMessages(@NonNull Result result) {
        try {
            Appoxee.instance().fetchInboxMessages(activity);
        } catch (Exception e) {
            result.error("fetchInboxMessages", e.getMessage(), null);
        }
    }

    private void getFcmToken(@NonNull Result result) {
        try {
            Appoxee.instance().getFcmToken(result::success);
        } catch (Exception e) {
            result.error("getFcmToken", e.getMessage(), null);
        }
    }

    private void setToken(String token, @NonNull Result result) {
        try {
            Appoxee.instance().setToken(token);
            result.success(token);
        } catch (Exception e) {
            result.error("setToken", e.getMessage(), null);
        }
    }

    private void startGeoFencing(@NonNull Result result) {
        try {
            Appoxee.instance().startGeoFencing(result::success);
        } catch (Exception e) {
            result.error("startGeoFencing", e.getMessage(), null);
        }
    }

    private void stopGeoFencing(@NonNull Result result) {
        try {
            Appoxee.instance().stopGeoFencing(result::success);
        } catch (Exception e) {
            result.error("stopGeoFencing", e.getMessage(), null);
        }
    }

    private void addTag(String tag, @NonNull Result result) {
        try {
            RequestStatus status = Appoxee.instance().addTag(tag);
            if (status == RequestStatus.SUCCESS) {
                result.success(tag);
            } else {
                result.error("addTag", "Error adding TAG!", null);
            }
        } catch (Exception e) {
            result.error("addTag", e.getMessage(), null);
        }
    }

    private void getTags(@NonNull Result result) {
        try {
            Set<String> tags = Appoxee.instance().getTags();
            result.success(tags);
        } catch (Exception e) {
            result.error("getTag", e.getMessage(), null);
        }
    }

    private void logOut(boolean pushEnabled, @NonNull Result result) {
        try {
            Appoxee.instance().logOut(application, pushEnabled);
            result.success(true);
        } catch (Exception e) {
            result.error("logOut", e.getMessage(), null);
        }
    }

    private void isDeviceRegistered(@NonNull Result result) {
        try {
            boolean isRegistered = Appoxee.instance().isDeviceRegistered();
            result.success(isRegistered);
        } catch (Exception e) {
            result.error("isDeviceRegistered", e.getMessage(), null);
        }
    }

//    private void setRemoteMessage(RemoteMessage remoteMessage, @NonNull Result result) {
//        try {
//            Appoxee.instance().setRemoteMessage(remoteMessage);
//        } catch (Exception e) {
//            result.error("", e.getMessage(), null);
//        }
//    }

    private AppoxeeOptions.Server getServerByIndex(int index) {
        if (index < 0 || index > AppoxeeOptions.Server.values().length) {
            throw new IndexOutOfBoundsException("Server must be one of the following: L3 [0], L3_US [1], EMC [2], EMC_US [3], CROC [4], TEST [5], TEST55 [6] and proper index provided.");
        }
        return AppoxeeOptions.Server.values()[index];
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        devLogger.d("attached to activity");
        this.activity = binding.getActivity();
        this.channel.setMethodCallHandler(this);
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        devLogger.d("detached from activity");
        this.activity = null;
        this.channel.setMethodCallHandler(null);
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        devLogger.d("reattached to activity on config changes");
        this.activity = binding.getActivity();
        this.channel.setMethodCallHandler(this);
    }

    @Override
    public void onDetachedFromActivity() {
        devLogger.d("detached from activity");
        this.activity = null;
        this.channel.setMethodCallHandler(null);
    }
}
