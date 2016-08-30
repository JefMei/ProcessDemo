package com.meimei.processdemo;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by 梅梅 on 2016/8/30.
 *
 */
public class AccessbilityUtil {

    final static String TAG = "AccessibilityUtil";

    /**
     * 用于判断是否开启了辅助功能
     * @param context
     * @return
     */
    public static boolean isAccessibilitySettingsOn(Context context){
        int accessibilityEnabled = 0;

        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        }catch (Settings.SettingNotFoundException e){
            Log.i(TAG,e.getMessage());
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;

    }

    /**
     * 调用方法isAccessibilitySettingsOn()方法判断是否开启辅助功能，没有则跳转到设置页面
     * @param context
     */
    public static void anyMethod(Context context) {
        // 判断辅助功能是否开启
        if (!isAccessibilitySettingsOn(context)) {
            // 引导至辅助功能设置页面
            context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        } else {
            // 执行辅助功能服务相关操作
        }
    }

}
