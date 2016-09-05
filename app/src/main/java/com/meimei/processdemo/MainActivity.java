package com.meimei.processdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.meimei.processdemo.Service.MyService;
import com.meimei.processdemo.Utils.AppLockUtil;

public class MainActivity extends Activity {

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.bt);


        /*//判断辅助功能有没有开
        AccessbilityUtil.anyMethod(this);*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppLockUtil.isPermissionForTest(MainActivity.this) == false){
                    Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this,"权限不够\n请打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾", Toast.LENGTH_LONG).show();
                }else {
                    //打开service
                    Intent intent = new Intent(MainActivity.this,MyService.class);
                    startService(intent);
                    Intent MyIntent = new Intent(Intent.ACTION_MAIN);
                    startActivity(MyIntent);
                    finish();
                }

            }
        });

    }
}
