package com.example.mapp_sdk;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.appoxee.Appoxee;
import com.appoxee.internal.logger.Logger;
import com.appoxee.internal.logger.LoggerFactory;

public class ActivityListener extends Activity {

    private final Logger devLogger= LoggerFactory.getDevLogger();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devLogger.d(getIntent());
        Intent intent = getIntent();
        Intent launchIntent = getDefaultActivityIntent();
        launchIntent.putExtra("action", intent.getAction());
        launchIntent.setData(intent.getData());
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(launchIntent);
        Appoxee.handleRichPush(Appoxee.instance().getLastActivity(),intent);
        finish();
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
