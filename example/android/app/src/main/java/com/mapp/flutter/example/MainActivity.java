package com.mapp.flutter.example;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapp.flutter.sdk.MappSdkPlugin;

import io.flutter.embedding.android.FlutterActivity;

public class MainActivity extends FlutterActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MappSdkPlugin.handleIntent(this,getIntent());
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        MappSdkPlugin.handleIntent(this, intent);
    }
}
