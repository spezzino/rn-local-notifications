package xyz.paraguaydev.reactnative.localnotifications.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ContextUtils {
    private boolean isApplicationInForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        if (processInfos != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
                if (processInfo.processName.equals(context.getPackageName())
                        && processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        && processInfo.pkgList.length > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
