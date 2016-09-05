package com.meimei.processdemo.Receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.meimei.processdemo.Service.MyService;

/**
 * Created by 梅梅 on 2016/9/5.
 */
public class MyReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MyService.class);
        context.startService(i);
    }
}
