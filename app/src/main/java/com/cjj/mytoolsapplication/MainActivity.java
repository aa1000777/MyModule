package com.cjj.mytoolsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.cjj.base.BaseActivity;
import com.cjj.base.DoubleClickExitHelper;
import com.cjj.mytoolsapplication.addlistdatalibrary.AddListDataActivity;
import com.cjj.mytoolsapplication.addlistdatalibrary.AddListDataActivity2;
import com.cjj.mytoolsapplication.circleImageView.CircleImageViewActivity;
import com.cjj.mytoolsapplication.crashlibrary.CrashActivity;
import com.cjj.mytoolsapplication.dialog.DialogActivity;
import com.cjj.mytoolsapplication.imagepicker.ImagePickerActivity;
import com.cjj.mytoolsapplication.okhttp.OkHttpActivity;
import com.cjj.mytoolsapplication.posterboardlibrary.PosterBoardActivity;
import com.cjj.mytoolsapplication.viewpagerlibrary.MyViewPagerActivity;
import com.cjj.zxinglibrary.activity.CaptureActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private DoubleClickExitHelper mDoubleClickExitHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mDoubleClickExitHelper = new DoubleClickExitHelper(this);
    }

    @OnClick({R.id.btn_image_picker, R.id.btn_image, R.id.btn_crash, R.id.btn_ok_http, R.id.btn_qr_scan, R.id.btn_dialog, R.id.btn_poster_board, R.id.btn_view_page, R.id.btn_add_list_data, R.id.btn_add_list_data2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_image_picker:
                //图片选择
                startActivity(new Intent(this, ImagePickerActivity.class));
                break;
            case R.id.btn_image:
                //图片处理
                startActivity(new Intent(this, CircleImageViewActivity.class));
                break;
            case R.id.btn_ok_http:
                //OkHttp
                startActivity(new Intent(this, OkHttpActivity.class));
                break;
            case R.id.btn_crash:
                //全局异常
                startActivity(new Intent(this, CrashActivity.class));
                break;
            case R.id.btn_qr_scan:
                //扫码
                startActivity(new Intent(this, CaptureActivity.class));
                break;
            case R.id.btn_dialog:
                startActivity(new Intent(this, DialogActivity.class));
                break;
            case R.id.btn_poster_board:
                startActivity(new Intent(this, PosterBoardActivity.class));
                break;
            case R.id.btn_view_page:
                startActivity(new Intent(this, MyViewPagerActivity.class));
                break;
            case R.id.btn_add_list_data:
                startActivity(new Intent(this, AddListDataActivity.class));
                break;
            case R.id.btn_add_list_data2:
                startActivity(new Intent(this, AddListDataActivity2.class));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return mDoubleClickExitHelper.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
