package com.cjj.mytoolsapplication.viewpagerlibrary;

import android.content.Context;
import android.view.View;

import com.cjj.addlistdatalibrary.CommonAdapter;
import com.cjj.addlistdatalibrary.ViewHolder;
import com.cjj.mytoolsapplication.R;

/**
 *
 * Created by 火蚁 on 15/4/9.
 */
public class ProjectAdapter extends CommonAdapter<Project> {

    public ProjectAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolder vh, final Project project) {
        // 2.显示相关信息
        vh.setText(R.id.tv_title, project.owner.name + " / " + project.name);

        // 判断是否有项目的介绍
        vh.setText(R.id.tv_description, project.description, R.string.msg_project_empty_description);
        // 显示项目的star、fork、language信息
        vh.setTextWithSemantic(R.id.tv_watch, project.watches_count+"", 0);
        vh.setTextWithSemantic(R.id.tv_star, project.stars_count+"", 0);
        vh.setTextWithSemantic(R.id.tv_fork, project.forks_count+"", 0);

        String language = project.language != null ? project.language : "";
        if (project.language != null) {
            vh.setTextWithSemantic(R.id.tv_lanuage, language, 0);
        } else {
            vh.getView(R.id.tv_lanuage).setVisibility(View.GONE);
        }

        // 1.加载头像
        String portraitURL = project.owner.new_portrait;
        if (portraitURL.endsWith("portrait.gif")) {
            vh.setImageResource(R.id.iv_portrait, R.drawable.mini_avatar);
        } else {
            vh.setImageForUrl(R.id.iv_portrait, portraitURL);
        }
        vh.getView(R.id.iv_portrait).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (project.owner == null) {
                    return;
                }
            }
        });
    }
}
