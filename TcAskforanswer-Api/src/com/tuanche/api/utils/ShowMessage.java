package com.tuanche.api.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ShowMessage {
    /**
     * Show Warning to user.
     */
    public static void showToast(Activity activity, String str) {
    	Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
    }
    public static void showToast(Context context, String str) {
    	Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
