package com.zaixiaoqu.appupdate.component.sdk;

import android.content.Context;
import android.util.Log;

public class UpdateChecker {


    public static void checkForDialog(Context context, Boolean isCanCancel) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_DIALOG, false, isCanCancel).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }
    }


    public static void checkForNotification(Context context) {
        if (context != null) {
            new CheckUpdateTask(context, Constants.TYPE_NOTIFICATION, false, true).execute();
        } else {
            Log.e(Constants.TAG, "The arg context is null");
        }

    }
}
