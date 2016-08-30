package com.meimei.processdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.bt);

        //判断辅助功能有没有开
        AccessbilityUtil.anyMethod(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开service
                Intent intent = new Intent(MainActivity.this,DetectionService.class);
                startService(intent);

            }
        });

    }
}
