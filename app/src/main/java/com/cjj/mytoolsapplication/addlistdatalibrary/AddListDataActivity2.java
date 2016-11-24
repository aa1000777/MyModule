package com.cjj.mytoolsapplication.addlistdatalibrary;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cjj.mytoolsapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddListDataActivity2 extends AppCompatActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_data);
        ButterKnife.bind(this);
        tvTitle.setText("Fragment列表");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, new AddListDataFragment()).commit();
    }

    @OnClick(R.id.tv_back)
    public void onClick() {
        finish();
    }
}