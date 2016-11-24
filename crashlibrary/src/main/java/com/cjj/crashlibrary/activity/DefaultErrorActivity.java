/*
 * Copyright 2015 Eduard Ereza Martínez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cjj.crashlibrary.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cjj.crashlibrary.CustomActivityOnCrash;
import com.cjj.crashlibrary.R;


public final class DefaultErrorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.customactivityoncrash_default_error_activity);

        //Close/restart button logic:
        //If a class if set, use restart.
        //Else, use close and just finish the app.
        //It is recommended that you follow this logic if implementing a custom error activity.
        Button restartButton = (Button) findViewById(R.id
                .customactivityoncrash_error_activity_restart_button);

        final Class<? extends Activity> restartActivityClass = CustomActivityOnCrash
                .getRestartActivityClassFromIntent(getIntent());

        if (restartActivityClass != null) {
            restartButton.setText(R.string.customactivityoncrash_error_activity_restart_app);
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DefaultErrorActivity.this, restartActivityClass);
                    CustomActivityOnCrash.restartApplicationWithIntent(DefaultErrorActivity.this,
                            intent);
                }
            });
        } else {
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomActivityOnCrash.closeApplication(DefaultErrorActivity.this);
                }
            });
        }

        Button moreInfoButton = (Button) findViewById(R.id
                .customactivityoncrash_error_activity_more_info_button);

        if (CustomActivityOnCrash.isShowErrorDetailsFromIntent(getIntent())) {

            moreInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //We retrieve all the error data and show it

//                    AlertDialog dialog = new AlertDialog.Builder(DefaultErrorActivity.this)
//                            .setTitle(R.string
// .customactivityoncrash_error_activity_error_details_title)
//                            .setMessage(CustomActivityOnCrash.getAllErrorDetailsFromIntent
// (DefaultErrorActivity.this, getIntent()))
//                            .setPositiveButton(R.string
// .customactivityoncrash_error_activity_error_details_close, null)
//                            .setNeutralButton(R.string
// .customactivityoncrash_error_activity_error_details_copy,
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            copyErrorToClipboard();
//                                            Toast.makeText(DefaultErrorActivity.this, R.string
// .customactivityoncrash_error_activity_error_details_copied, Toast.LENGTH_SHORT).show();
//                                        }
//                                    })
//                            .show();
//                    TextView textView = (TextView) dialog.findViewById(android.R.id.message);
//                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
// .getDimension(R.dimen.customactivityoncrash_error_activity_error_details_text_size));
                    // 发送异常报告
                    Intent i = new Intent(Intent.ACTION_SEND);
                    // i.setType("text/plain"); //模拟器
                    i.setType("message/rfc822"); // 真机
                    // 接收错误报告的邮箱地址
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"app@oschina.cn"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "GIT@OSC,Android客户端 - 错误报告");
                    i.putExtra(Intent.EXTRA_TEXT, CustomActivityOnCrash.getAllErrorDetailsFromIntent
                            (DefaultErrorActivity.this, getIntent()));
                    DefaultErrorActivity.this.startActivity(Intent.createChooser(i,
                            "发送错误报告"));
                }
            });
        } else {
            moreInfoButton.setVisibility(View.GONE);
        }

        int defaultErrorActivityDrawableId = CustomActivityOnCrash
                .getDefaultErrorActivityDrawableIdFromIntent(getIntent());
        ImageView errorImageView = ((ImageView) findViewById(R.id
                .customactivityoncrash_error_activity_image));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            errorImageView.setImageDrawable(getResources().getDrawable
                    (defaultErrorActivityDrawableId, getTheme()));
        } else {
            //noinspection deprecation
            errorImageView.setImageDrawable(getResources().getDrawable
                    (defaultErrorActivityDrawableId));
        }
    }

    private void copyErrorToClipboard() {
        String errorInformation =
                CustomActivityOnCrash.getAllErrorDetailsFromIntent(DefaultErrorActivity.this,
                        getIntent());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(getString(R.string
                            .customactivityoncrash_error_activity_error_details_clipboard_label),
                    errorInformation);
            clipboard.setPrimaryClip(clip);
        } else {
            //noinspection deprecation
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                    getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(errorInformation);
        }
    }
}
