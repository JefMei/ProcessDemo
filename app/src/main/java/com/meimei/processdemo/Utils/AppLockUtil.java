package com.meimei.processdemo.Utils;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.meimei.processdemo.Lockactivity.LockActivity;
import com.meimei.processdemo.Lockactivity.LockActivity1;
import com.meimei.processdemo.Lockactivity.LockActivity2;
import com.meimei.processdemo.Lockactivity.LockActivity3;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by 梅梅 on 2016/9/5.
 */
public class AppLockUtil {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean queryUsageStats(Context context){
        final String TAG = "queryUsageStats";

        class RecentUseComparator implements Comparator<UsageStats> {

            @Override
            public int compare(UsageStats lhs, UsageStats rhs) {
                return (lhs.getLastTimeUsed() > rhs.getLastTimeUsed()) ? -1 : (lhs.getLastTimeUsed() == rhs.getLastTimeUsed()) ? 0 : 1;
            }
        }

        RecentUseComparator mRecentUseComparator = new RecentUseComparator();
        long ts = System.currentTimeMillis();
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
        List<UsageStats> usageStatses = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,ts -1000 * 10,ts);
        /*if (usageStatses == null || usageStatses.size() == 0){
            if (isPermissionForTest(context) == false){
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Toast.makeText(context,"权限不够\n请打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾", Toast.LENGTH_LONG).show();
            }
            return false;
        }*/
        if (isPermissionForTest(context) == false){
            return false;
        }
        Collections.sort(usageStatses,mRecentUseComparator);
        String currentTopPackage = usageStatses.get(0).getPackageName();

        //将包名的最后一段保存用于判断比较方便
        String[] aa = currentTopPackage.split("\\.");
        currentTopPackage = aa[aa.length-1].toString();


        Log.i(TAG,currentTopPackage);
        if (isCurrentDetection(currentTopPackage)){
            Log.i(TAG,"在前台");
        }else
        {
            Log.i(TAG,"在后台");

            int ran_num = new Random().nextInt(4);   //生成一个0-3的随机数,随机选择lockactivity
            Intent intent = null;
            switch (ran_num){
                case 0:
                    intent = new Intent(context,LockActivity.class);
                    break;
                case 1:
                    intent = new Intent(context,LockActivity1.class);
                    break;
                case 2:
                    intent = new Intent(context,LockActivity2.class);
                    break;
                case 3:
                    intent = new Intent(context,LockActivity3.class);
                    break;
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        return true;

    }

    /**
     * 判断是否开启了权限
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isPermissionForTest(Context context){
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isCurrentDetection(String Currentpackage ){

        //通过包名的最后一部分判断 是否和 任务栏 或 主界面launcher 或 本app 的包名相等
        if (Currentpackage.equals("systemui") ){
            return true;
        }
        if ( Currentpackage.equals("launcher")){
            return true;
        }
        if ( Currentpackage.equals("processdemo")){
            return true;
        }
        return false;
    }
}
