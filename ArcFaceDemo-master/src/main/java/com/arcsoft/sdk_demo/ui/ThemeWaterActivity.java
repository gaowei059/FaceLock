package com.arcsoft.sdk_demo.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.arcsoft.sdk_demo.DetecterActivity;
import com.arcsoft.sdk_demo.lock.LockerProtectService;
import com.arcsoft.sdk_demo.lock.LockerService;
import com.arcsoft.sdk_demo.theme.RootView;
import com.arcsoft.sdk_demo.util.Config;
import com.arcsoft.sdk_demo.util.Constant;

import java.lang.reflect.Method;

public class ThemeWaterActivity extends Activity {
    private BroadcastReceiver mReceiver = null;
    private RootView mRootView = null;
    private static final boolean sIsFull = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (sIsFull) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                |View.SYSTEM_UI_FLAG_FULLSCREEN

                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        mRootView = new RootView(this);
        setContentView(mRootView);
        createConfig();
        createReceiver();
    }

    private void createConfig() {

        Bundle bundle = new Bundle();
        bundle.putBoolean(Constant.IS_SHOW_TIME, true);
        bundle.putBoolean(Constant.IS_LOCK_SOUND, false);
        bundle.putBoolean(Constant.IS_UNLOCK_SOUND, false);
        bundle.putBoolean(Constant.IS_LOCK_QUAKE, true);

        mRootView.onStart(bundle);
        mRootView.onShow();

    }

    private void createReceiver() {
        // 创建广播接收器
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                if (action.equals("com.zero.locker.unlock")) {
                    String strTheme = intent.getStringExtra("action");
                    if (strTheme != null) {
                        finish();
                        Intent intent1 = new Intent(Intent.ACTION_MAIN,null);
                        intent1.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent1);
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zero.locker.unlock");
        // 注册广播接收器
        this.registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRootView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRootView.onResume();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRootView.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mRootView != null) {
            mRootView.onDestroy();
            mRootView = null;
        }
        this.unregisterReceiver(mReceiver);
        super.onDestroy();
        Config.sIsLock = true;
        startService(new Intent(ThemeWaterActivity.this, LockerService.class));
        startService(new Intent(ThemeWaterActivity.this, LockerProtectService.class));
        LockerProtectService.startPollingService(ThemeWaterActivity.this, 60,
                LockerProtectService.class);
        super.onDestroy();
    }
}
