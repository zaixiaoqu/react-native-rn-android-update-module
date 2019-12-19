package com.zaixiaoqu.appupdate.component.sdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.zaixiaoqu.appupdate.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckUpdateTask extends AsyncTask<Void, Void, String> {

    private ProgressDialog dialog;
    private Context mContext;
    private int mType;
    private boolean mShowProgressDialog;
    private static String url = Constants.UPDATE_URL;
    private Boolean isCanCancel = false;

    CheckUpdateTask(Context context, int type, boolean showProgressDialog, Boolean isCanCancel) {
        this.mContext = context;
        this.mType = type;
        this.mShowProgressDialog = showProgressDialog;
        this.isCanCancel = isCanCancel;

    }

    protected void onPreExecute() {
        if (mShowProgressDialog) {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage(mContext.getString(R.string.android_auto_update_dialog_checking));
            dialog.show();
        }
    }


    @Override
    protected void onPostExecute(String result) {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        if (!TextUtils.isEmpty(result)) {
            parseJson(result);
        }
    }

    private void parseJson(String result) {
        try {

            JSONObject obj = new JSONObject(result);
            String updateMessage = obj.getString(Constants.APK_UPDATE_CONTENT);
            String apkUrl = obj.getString(Constants.APK_DOWNLOAD_URL);
            int apkCode = obj.getInt(Constants.APK_VERSION_CODE);

            int versionCode = AppUtils.getVersionCode(mContext);

            if (apkCode > versionCode) {
                if (mType == Constants.TYPE_NOTIFICATION) {
                    new NotificationHelper(mContext).showNotification(updateMessage, apkUrl);
                } else if (mType == Constants.TYPE_DIALOG) {
                    showDialog(mContext, updateMessage, apkUrl);
                }
            } else if (mShowProgressDialog) {
                // 已经是最新的版本, 不用提示
                // Toast.makeText(mContext, mContext.getString(R.string.android_auto_update_toast_no_new_update), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.e(Constants.TAG, "parse json error");
        }
    }


    /**
     * Show dialog
     */
    private void showDialog(Context context, String content, String apkUrl) {
        UpdateDialog.show(context, content, apkUrl, this.isCanCancel);
    }


    @Override
    protected String doInBackground(Void... args) {
        return HttpUtils.get(url);
    }

    /**
     * 设置更新的url
     */
    public static void setUpdateServerUrl(String resetUrl) {
        url = resetUrl;
    }
}
