package com.cjj.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;


public abstract class BaseActivity extends AppCompatActivity {

    // 是否可以返回
    protected static boolean isCanBack;

    protected ActionBar mActionBar;

    protected String mTitle;

    protected String mSubTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将activity加入到AppManager堆栈中
        AppManager.getAppManager().addActivity(this);
    }

    // 关闭该Activity
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    public void setActionBarTitle(String title) {
        mActionBar.setTitle(title);
    }

    public void setActionBarSubTitle(String subTitle) {
        mActionBar.setSubtitle(subTitle);
    }

    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //setOverflowIconVisible(featureId, menu);
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }
}
