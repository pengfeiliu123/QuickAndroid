package com.lpf.playlight;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import java.util.List;

/**
 *
 */
public class AppTool {

    public static void createShortcut(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            int createdShortcut = preferences.getInt("createdShortcut", 0);
            if (createdShortcut == 0) {
                //创建快捷方式的Intent
                Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
                //不允许重复创建
                shortcutIntent.putExtra("duplicate", false);
                //快捷方式名称
                shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getResources().getString(R.string.app_name));
                //快捷图片
                Parcelable icon = Intent.ShortcutIconResource.fromContext(context, R.mipmap.ic_launcher);
                shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
                //点击快捷图片，运行的程序主入口
                shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(context, MainActivity.class));
                //发送广播。OK
                context.sendBroadcast(shortcutIntent);
                preferences.edit().putInt("createdShortcut", 1).commit();
            }
        }
    }

    public static boolean isAppOnForeground(Context context) {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    public static boolean isAppInstalled(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }


    /**
     * 判断是否平板设备
     * @param context
     * @return true:平板,false:手机
     */
    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
