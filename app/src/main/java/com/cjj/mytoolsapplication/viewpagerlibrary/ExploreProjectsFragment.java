package com.cjj.mytoolsapplication.viewpagerlibrary;

import android.os.Bundle;

import com.cjj.addlistdatalibrary.BaseSwipeRefreshHintFragment;
import com.cjj.addlistdatalibrary.CommonAdapter;
import com.cjj.mytoolsapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.Urls;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/15.
 */

public class ExploreProjectsFragment extends BaseSwipeRefreshHintFragment<Project> {
    public final static String EXPLORE_TYPE = "explore_type";
    public final static byte TYPE_FEATURED = 0x0;

    public final static byte TYPE_POPULAR = 0x1;

    public final static byte TYPE_NEWLY = 0x2;

    public final static byte TYPE_LATEST = 0x3;
    private byte type = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        type = args.getByte(EXPLORE_TYPE);
    }

    @Override
    public CommonAdapter getAdapter() {
        return new ProjectAdapter(getActivity(), R.layout.list_item_project);
    }

    @Override
    public List getDatas(String json) {
        Type type = new TypeToken<ArrayList<Project>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    @Override
    public void requestData() {
        switch (type) {
            case TYPE_FEATURED:
                getDataProject(Urls.USER_FEATURED);
                break;
            case TYPE_POPULAR:
                getDataProject(Urls.USER_POPULAR);
                break;
            case TYPE_NEWLY:
                getDataProject(Urls.USER_FEATURED);
                break;
            case TYPE_LATEST:
                getDataProject(Urls.USER_LATEST);
                break;
        }
    }

    @Override
    public void onItemClick(int position, Project data) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
