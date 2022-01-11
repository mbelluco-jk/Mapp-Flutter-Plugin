package com.example.mapp_sdk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.appoxee.internal.logger.Logger;
import com.appoxee.internal.logger.LoggerFactory;

public class HelperActivity extends Activity {

    private final Logger devLogger = LoggerFactory.getDevLogger();

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devLogger.d("Action: - " + (getIntent() != null ? getIntent().getAction() : "null"));
        Intent intent = getIntent();
        emit(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        devLogger.d("Action: - " + (intent != null ? intent.getAction() : "null"));
        emit(intent);
    }

    private void emit(Intent intent) {
        Uri data=intent!=null ? intent.getData() : null;
        String action = intent != null ? intent.getAction() : null;

        if (action != null && !action.isEmpty() && data!=null) {
            EventEmitter.getInstance().sendEvent(new InAppDeepLinkEvent(data, action));
        }

        finish();
    }
}