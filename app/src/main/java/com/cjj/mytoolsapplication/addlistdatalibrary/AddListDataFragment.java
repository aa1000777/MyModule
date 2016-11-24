package com.cjj.mytoolsapplication.addlistdatalibrary;

import com.cjj.addlistdatalibrary.BaseSwipeRefreshFragment;
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

import butterknife.ButterKnife;

public class AddListDataFragment extends BaseSwipeRefreshFragment<Project> {

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
        getDataProject(Urls.USER_FEATURED);
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
