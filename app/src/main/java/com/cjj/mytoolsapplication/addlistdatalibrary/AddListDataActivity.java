package com.cjj.mytoolsapplication.addlistdatalibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjj.addlistdatalibrary.BaseSwipeRefreshActivity;
import com.cjj.addlistdatalibrary.CommonAdapter;
import com.cjj.mytoolsapplication.R;
import com.cjj.mytoolsapplication.viewpagerlibrary.Project;
import com.cjj.mytoolsapplication.viewpagerlibrary.ProjectAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.Urls;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddListDataActivity extends BaseSwipeRefreshActivity<Project> {

    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.content)
    RelativeLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_data);
        ButterKnife.bind(this);
        tvTitle.setText("Activity列表");
    }

    @Override
    public void setListView(View mContentView) {
        if (content.getChildCount() > 0) {
            content.removeAllViews();
        }
        content.addView(mContentView);
    }


    @Override
    public CommonAdapter getAdapter() {
        return new ProjectAdapter(this, R.layout.list_item_project);
    }

    @Override
    public List getData(String json) {
        Type type = new TypeToken<ArrayList<Project>>() {
        }.getType();
        return new Gson().fromJson(json, type);
//        return null;
    }

    @Override
    public void requestData() {
        getDataProject(Urls.USER_FEATURED);
    }

    @Override
    public void onItemClick(int position, Project data) {

    }

    @OnClick(R.id.tv_back)
    public void onClick() {
        finish();
    }
}
