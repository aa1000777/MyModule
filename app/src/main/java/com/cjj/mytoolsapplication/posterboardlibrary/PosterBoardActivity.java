package com.cjj.mytoolsapplication.posterboardlibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cjj.mytoolsapplication.R;
import com.cjj.posterboardlibrary.ImageOnClickListener;
import com.cjj.posterboardlibrary.MyImgScroll;

/**
 * 广告板
 */
public class PosterBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_board);
        initMyImgScroll_Int();
        initMyImgScroll_String();
    }

    private void initMyImgScroll_Int() {
        MyImgScroll myImgScroll = (MyImgScroll) findViewById(R.id.myvp);
        LinearLayout ll_vb = (LinearLayout) findViewById(R.id.vb);
        int[] image = new int[]{R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
        myImgScroll.start(this, ll_vb, image, new MyImageOnClickListener());
    }

    private void initMyImgScroll_String() {
        MyImgScroll myImgScroll = (MyImgScroll) findViewById(R.id.myvp2);
        LinearLayout ll_vb = (LinearLayout) findViewById(R.id.vb2);
        String url1 = "http://ngate.oss-cn-shanghai.aliyuncs.com/e1110f97-df37-4570-949a-565f18ecc77a.jpg";
        String url2 = "http://ngate.oss-cn-shanghai.aliyuncs.com/2f5e3eef-854b-42a4-bca2-60d55bf1b65e.jpg";
        String url3 = "http://ngate.oss-cn-shanghai.aliyuncs.com/9031508c-6c6d-4194-b5e1-553895af7644.jpg";
        String[] image = new String[]{url1, url2, url3};
        myImgScroll.start(this, ll_vb, image, new MyImageOnClickListener());
    }

    //级联菜单选择回调接口
    class MyImageOnClickListener implements ImageOnClickListener {
        @Override
        public void click(View view, int position) {
            Toast.makeText(PosterBoardActivity.this, "真的有用么？" + position, Toast.LENGTH_SHORT).show();
        }
    }
}
