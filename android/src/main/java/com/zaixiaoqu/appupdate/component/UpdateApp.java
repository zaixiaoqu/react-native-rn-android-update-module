package com.zaixiaoqu.appupdate.component;

import android.os.Build;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.zaixiaoqu.appupdate.component.sdk.CheckUpdateTask;
import com.zaixiaoqu.appupdate.component.sdk.UpdateChecker;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public class UpdateApp extends ReactContextBaseJavaModule {

    private ReactContext reactContext;
    private static final String Android_User_Agent = "DefaultAndroidUserAgent";

    public UpdateApp(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    public String getDefaultWebViewUserAgent (){
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return WebSettings.getDefaultUserAgent(getCurrentActivity());
            }
        } catch (Exception e) {
        }
        return "";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(Android_User_Agent, this.getDefaultWebViewUserAgent());
        return constants;
    }

    @Override
    public String getName() {
        return "UpdateApp";
    }

    @ReactMethod
    public void updateDialog (){
        UpdateChecker.checkForDialog(getCurrentActivity(), false);
    }

    @ReactMethod
    public void updateDialogOk (Boolean isCanCancel){
        UpdateChecker.checkForDialog(getCurrentActivity(), isCanCancel);
    }

    @ReactMethod
    public void updateNotification() {
        UpdateChecker.checkForNotification(getCurrentActivity());
    }

    @ReactMethod
    public void setUpdateServerUrl(String url){
        CheckUpdateTask.setUpdateServerUrl(url);
    }

}