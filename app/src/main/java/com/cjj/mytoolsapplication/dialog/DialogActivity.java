package com.cjj.mytoolsapplication.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cjj.mytoolsapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends AppCompatActivity {
    @Bind(R.id.btn_progress_dialog)
    Button btnProgressDialog;
    private ProgressDialog mLoginProgressDialog;
    private Dialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_progress_dialog, R.id.btn_dialog1, R.id.btn_dialog2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_progress_dialog:
                btnProgressDialog();
                break;
            case R.id.btn_dialog1:
                setDialog1();
                break;
            case R.id.btn_dialog2:
                setDialog2();
                break;
        }
    }

    private void btnProgressDialog() {
        if (mLoginProgressDialog == null) {
            mLoginProgressDialog = new ProgressDialog(this);
            btnProgressDialog.setText("ProgressDialog(隐藏)");
            mLoginProgressDialog.setCancelable(true);
            mLoginProgressDialog.setCanceledOnTouchOutside(false);
            mLoginProgressDialog.setMessage(getString(R.string.loading_tips));
            mLoginProgressDialog.show();
        } else {
            if (mLoginProgressDialog.isShowing()) {
                mLoginProgressDialog.dismiss();
            }
            btnProgressDialog.setText("ProgressDialog(显示)");
            mLoginProgressDialog = null;
        }
    }

    /**
     *
     *****************************************************
     * 方法简介: 显示两个按钮的Dialog
     *****************************************************
     */
    public void setDialog1() {
        // 新建自己风格的dialog
        dialog = new Dialog(DialogActivity.this, R.style.mydialog);
        // 绑定布局
        dialog.setContentView(R.layout.confirmfragmentdlg);
        TextView DialogMessage = (TextView) dialog
                .findViewById(R.id.DialogMessage);
        DialogMessage.setText("此用户以经存在，是否继续？");
        Button closeBtn = (Button) dialog.findViewById(R.id.closeBtn);
        Button submitBtn = (Button) dialog.findViewById(R.id.submitBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     *
     *****************************************************
     * 方法简介: 显示三个按钮的Dialog
     *****************************************************
     */
    public void setDialog2() {
        // 新建自己风格的dialog
        dialog = new Dialog(DialogActivity.this, R.style.my_dialog);
        // 绑定布局
        dialog.setContentView(R.layout.confirmfragmentdlg);
        TextView DialogMessage = (TextView) dialog
                .findViewById(R.id.DialogMessage);
        DialogMessage.setText("此用户以经存在，是否继续？");
        Button closeBtn = (Button) dialog.findViewById(R.id.closeBtn);
        Button submitBtn = (Button) dialog.findViewById(R.id.submitBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
