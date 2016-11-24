package com.cjj.mytoolsapplication.crashlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cjj.mytoolsapplication.R;

/**
 * 报错测试页面
 */
public class CrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        //这里报错
        ((TextView)findViewById(R.id.tv_title)).setText("报错了");
    }
}
