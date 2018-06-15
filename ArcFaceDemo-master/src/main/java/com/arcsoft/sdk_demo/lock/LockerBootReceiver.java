package com.arcsoft.sdk_demo.lock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.arcsoft.sdk_demo.util.Config;

/**
 * Locker 自启动接收器
 */
public class LockerBootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Config.sIsLock){
            context.startService(new Intent(context, LockerService.class));
        }
    }
}
