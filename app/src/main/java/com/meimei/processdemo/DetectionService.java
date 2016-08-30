package com.meimei.processdemo;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.meimei.processdemo.Lockactivity.LockActivity;
import com.meimei.processdemo.Lockactivity.LockActivity1;
import com.meimei.processdemo.Lockactivity.LockActivity2;
import com.meimei.processdemo.Lockactivity.LockActivity3;

import java.util.Random;

/**
 * Created by 梅梅 on 2016/8/30.
 */
public class DetectionService extends AccessibilityService{

    final static String TAG = "DetectionService";

    static String foregroundPackageName;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand");
        return 0;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG,"onAccessibilityEvent");
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            /*
            如果与DeectionService 相同进程，直接比较foregroundPackageName的值即可
            如果在不同进程，可以利用Intent 或 bind service 进行通信
             */

            foregroundPackageName = event.getPackageName().toString();

            //将包名的最后一段保存用于判断比较方便
            String[] aa = foregroundPackageName.split("\\.");
            foregroundPackageName = aa[aa.length-1].toString();


            Log.i(TAG,foregroundPackageName);
            if (DetectionService.isForegroundPkgViaDetectionService(getPackageName())){
                Log.i(TAG,"在前台");
            }else
            {
                Log.i(TAG,"在后台");

                int ran_num = new Random().nextInt(4);   //生成一个0-3的随机数,随机选择lockactivity
                Intent intent = null;
                switch (ran_num){
                    case 0:
                        intent = new Intent(this,LockActivity.class);
                        break;
                    case 1:
                        intent = new Intent(this,LockActivity1.class);
                        break;
                    case 2:
                        intent = new Intent(this,LockActivity2.class);
                        break;
                    case 3:
                        intent = new Intent(this,LockActivity3.class);
                        break;
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.i(TAG,"onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.i(TAG,"onServiceConnected");
    }

    /**
     * 判断本应用或者任务栏或者系统的launcher  是否处于前台
     * @param packageName
     * @return
     */
    public static boolean isForegroundPkgViaDetectionService(String packageName){

        //通过包名的最后一部分判断 是否和 任务栏 或 主界面launcher 或 本app 的包名相等
        if (foregroundPackageName.equals("systemui") ){
            return true;
        }
        if ( foregroundPackageName.equals("launcher")){
            return true;
        }
        if ( foregroundPackageName.equals("processdemo")){
            return true;
        }
        return false;
    }
}
