package com.mapp.flutter.sdk;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.appoxee.Appoxee;
import com.appoxee.internal.logger.Logger;
import com.appoxee.internal.logger.LoggerFactory;

import io.flutter.embedding.android.FlutterActivity;

public class ActivityListener extends FlutterActivity {

    private final Logger devLogger= LoggerFactory.getDevLogger();
    private final Handler handler=new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Intent launchIntent = getDefaultActivityIntent();
        launchIntent.putExtra("action", intent.getAction());
        launchIntent.setData(intent.getData());
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(launchIntent);

        handler.postDelayed(() -> {
            Appoxee.handleRichPush(Appoxee.instance().getLastActivity(), getIntent());
            finish();
        }, 1000);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        devLogger.d(getIntent());
        Appoxee.handleRichPush(this, intent);
    }

    private Intent getDefaultActivityIntent() {
        PackageManager packageManager = getPackageManager();
        return packageManager.getLaunchIntentForPackage(getPackageName());
    }
}
