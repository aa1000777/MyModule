package com.cjj.mytoolsapplication.okhttp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cjj.mytoolsapplication.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.Urls;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callbackutils.StringDialogCallback;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        ButterKnife.bind(this);
        mContext = this;
    }

    @OnClick({R.id.btn_get, R.id.btn_post})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                btnGet();
                break;
            case R.id.btn_post:
                break;
        }
    }

    private void btnGet() {
        OkGo.get(Urls.formUrl(Urls.USER_FEATURED))//
                .tag(this)//
                .cacheMode(CacheMode.NO_CACHE)//缓存模式，详细请看缓存介绍
//                .headers("header1", "headerValue1")//
//                .params("param1", "paramValue1")//
                .execute(new StringDialogCallback(this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        handleResponse(s, call, response);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        handleError(call, response);
                    }
                });
    }


    private void handleResponse(String s, Call call, Response response) {
        Toast.makeText(mContext, "" + s, Toast.LENGTH_SHORT).show();
    }

    private void handleError(Call call, Response response) {
        Toast.makeText(mContext, "出错了。。。", Toast.LENGTH_SHORT).show();
    }

}
