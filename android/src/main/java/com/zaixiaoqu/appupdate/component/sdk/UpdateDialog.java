package com.zaixiaoqu.appupdate.component.sdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.zaixiaoqu.appupdate.R;

class UpdateDialog {


    static void show(final Context context, String content, final String downloadUrl, Boolean isCanCancel) {
        if (isContextValid(context)) {
            AlertDialog.Builder appDownurlDialog = new AlertDialog.Builder(context);
            appDownurlDialog.setTitle(R.string.android_auto_update_dialog_title)
                    .setMessage(content)
                    .setPositiveButton(R.string.android_auto_update_dialog_btn_download, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            goToDownload(context, downloadUrl);
                        }
                    });
            if (isCanCancel) {
                appDownurlDialog.setNegativeButton(
                        R.string.android_auto_update_dialog_btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
            }

            appDownurlDialog.setCancelable(false).show();
        }
    }

    private static boolean isContextValid(Context context) {
        return context instanceof Activity && !((Activity) context).isFinishing();
    }


    private static void goToDownload(Context context, String downloadUrl) {
        Intent intent = new Intent(context.getApplicationContext(), DownloadService.class);
        intent.putExtra(Constants.APK_DOWNLOAD_URL, downloadUrl);
        context.startService(intent);
    }
}
