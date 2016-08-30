package com.meimei.processdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *  随便一个用于跳转的界面
 */
public class LockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        Button button = (Button) findViewById(R.id.bt);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MyIntent = new Intent(Intent.ACTION_MAIN);
                MyIntent.addCategory(Intent.CATEGORY_HOME);
                startActivity(MyIntent);

                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent MyIntent = new Intent(Intent.ACTION_MAIN);
        MyIntent.addCategory(Intent.CATEGORY_HOME);
        startActivity(MyIntent);

        finish();
    }
}
