package com.arcsoft.sdk_demo.theme;

import android.os.Bundle;

/**
 * 生命周期接口
 */
public interface ILockView {
    
    void onStart(Bundle bundle);
    
    void onResume();
    
    void onShow();
    
    void onPause();
    
    void onStop();
    
    void onDestroy();
    
    void onMonitor(Bundle bundle);
}
