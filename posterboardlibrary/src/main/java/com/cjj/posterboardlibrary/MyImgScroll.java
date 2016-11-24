package com.cjj.posterboardlibrary;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.path;

/**
 * 图片滚动类（广告板使用）
 */
public class MyImgScroll extends ViewPager {
    Activity mActivity; // 上下文
    List<View> mListViews; // 图片组
    int mScrollTime = 0;
    Timer timer;
    int oldIndex = 0;
    int curIndex = 0;
    private int mDuration = 5000;
    boolean yesTow = false;
    ImageOnClickListener clickListener;

    public MyImgScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void start(Activity activity, LinearLayout ovalLayout, int[] images) {
        start(activity, ovalLayout, images, mDuration, R.layout.ad_bottom_item, R.id
                .ad_item_v, R.drawable.dot_focused, R.drawable.dot_normal);
    }

    public void start(Activity activity, LinearLayout ovalLayout, String[] images) {
        start(activity, ovalLayout, null, images, mDuration, R.layout.ad_bottom_item, R.id
                .ad_item_v, R.drawable.dot_focused, R.drawable.dot_normal);
    }

    public void start(Activity activity, LinearLayout ovalLayout, int[] images, ImageOnClickListener onClickListener) {
        this.clickListener = onClickListener;
        start(activity, ovalLayout, images, mDuration, R.layout.ad_bottom_item, R.id
                .ad_item_v, R.drawable.dot_focused, R.drawable.dot_normal);
    }

    public void start(Activity activity, LinearLayout ovalLayout, String[] images, ImageOnClickListener onClickListener) {
        this.clickListener = onClickListener;
        start(activity, ovalLayout, null, images, mDuration, R.layout.ad_bottom_item, R.id
                .ad_item_v, R.drawable.dot_focused, R.drawable.dot_normal);
    }

    /**
     * urlIP 图片拼接IP
     */
    public void start(Activity activity, LinearLayout ovalLayout, String urlIP, String[] images) {
        start(activity, ovalLayout, urlIP, images, mDuration, R.layout.ad_bottom_item, R.id
                .ad_item_v, R.drawable.dot_focused, R.drawable.dot_normal);
    }


    /**
     * 开始广告滚动
     *
     * @param activity         显示广告的主界面
     * @param ovalLayout       圆点容器,可为空,LinearLayout类型
     * @param images           图片列表, 不能为null ,最少一张
     * @param scrollTime       滚动间隔 ,0为不滚动
     * @param ovalLayoutId     ovalLayout为空时 写0, 圆点layout XMl
     * @param ovalLayoutItemId ovalLayout为空时 写0,圆点layout XMl 圆点XMl下View ID
     * @param focusedId        ovalLayout为空时 写0, 圆点layout XMl 选中时的动画
     * @param normalId         ovalLayout为空时 写0, 圆点layout XMl 正常时背景
     */
    public void start(Activity activity, LinearLayout ovalLayout, String urlIP, String[] images, int scrollTime,
                      int ovalLayoutId, int ovalLayoutItemId,
                      int focusedId, int normalId) {
        mActivity = activity;
        mListViews = new ArrayList<View>();
        for (int i = 0; i < images.length; i++) {
            addStringImage(activity, urlIP, images[i], i);
        }
        if (images.length == 2) {
            yesTow = true;
            for (int i = 0; i < images.length; i++) {
                addStringImage(activity, urlIP, images[i], i);
            }
        }
        mScrollTime = scrollTime;
        // 设置圆点
        setOvalLayout(ovalLayout, ovalLayoutId, ovalLayoutItemId, focusedId,
                normalId);
        this.setAdapter(new MyPagerAdapter());// 设置适配器
        setAnimation(scrollTime);//设置动画
        setSelect();//设置选种
    }

    private void addStringImage(Activity activity, String urlIP, String images, final int i) {
        ImageView image = new ImageView(activity);
        if (clickListener != null) {
            image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.click(view, i);
                }
            });
        }
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String uri = "";
        if (TextUtils.isEmpty(urlIP)) {
            uri = images;
        } else {
            uri = urlIP + images;
        }
        Glide.with(activity)//配置上下文
                .load(uri != null ? uri : path)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.drawable.morentupian)           //设置错误图片
                .placeholder(R.drawable.morentupian)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
                .into(image);
        mListViews.add(image);
    }

    private void setAnimation(int scrollTime) {
        System.out.println("scrollTime:" + scrollTime);
        System.out.println("scrollTimesize:" + mListViews.size());
        if (scrollTime != 0 && mListViews.size() > 1) {
            // 设置滑动动画时间 ,如果用默认动画时间可不用 ,反射技术实现
            new FixedSpeedScroller(mActivity).setDuration(this, 700);
            // 触摸时停止滚动
            this.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startTimer();
                    } else {
                        stopTimer();
                    }
                    return false;
                }
            });
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void start(Activity mainActivity, LinearLayout ovalLayout, int[] images, int scrollTime,
                      int ovalLayoutId, int ovalLayoutItemId,
                      int focusedId, int normalId) {
        mActivity = mainActivity;
        mListViews = new ArrayList<View>();
        for (int i = 0; i < images.length; i++) {
            addIntImage(mainActivity, images[i], i);
        }
        if (images.length == 2) {
            yesTow = true;
            for (int i = 0; i < images.length; i++) {
                addIntImage(mainActivity, images[i], i);
            }
        }
        mScrollTime = scrollTime;
        // 设置圆点
        setOvalLayout(ovalLayout, ovalLayoutId, ovalLayoutItemId, focusedId,
                normalId);
        this.setAdapter(new MyPagerAdapter());// 设置适配器
        setAnimation(scrollTime);
        setSelect();
    }

    private void setSelect() {
        if (mListViews.size() > 1) {
            this.setCurrentItem((Integer.MAX_VALUE / 2)
                    - (Integer.MAX_VALUE / 2) % mListViews.size());// 设置选中为中间/图片为和第0张一样
            startTimer();
        }
    }

    private void addIntImage(Activity mainActivity, int image, final int i) {
        ImageView imageView2 = new ImageView(mainActivity);
        if (clickListener != null) {
            imageView2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.click(view, i);
                }
            });
        }
        imageView2.setImageResource(image);
        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mListViews.add(imageView2);
    }


    // 设置圆点
    @SuppressWarnings("deprecation")
    private void setOvalLayout(final LinearLayout ovalLayout, int ovalLayoutId,
                               final int ovalLayoutItemId, final int focusedId, final int normalId) {
        if (ovalLayout != null) {
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            int size = mListViews.size();
            if (size == 1) {
                ovalLayout.setVisibility(View.GONE);
            }
            if (yesTow) {
                size = 2;
            }
            for (int i = 0; i < size; i++) {
                ovalLayout.addView(inflater.inflate(ovalLayoutId, null));

            }
            // 选中第一个
            ovalLayout.getChildAt(0).findViewById(ovalLayoutItemId)
                    .setBackgroundResource(focusedId);
            this.setOnPageChangeListener(new OnPageChangeListener() {
                public void onPageSelected(int i) {
                    int size = mListViews.size();
                    if (yesTow) {
                        size = 2;
                    }
                    curIndex = i % size;
                    // 取消圆点选中
                    ovalLayout.getChildAt(oldIndex)
                            .findViewById(ovalLayoutItemId)
                            .setBackgroundResource(normalId);
                    // 圆点选中
                    ovalLayout.getChildAt(curIndex)
                            .findViewById(ovalLayoutItemId)
                            .setBackgroundResource(focusedId);
                    oldIndex = curIndex;
                }

                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                public void onPageScrollStateChanged(int arg0) {
                }
            });
        }
    }

    /**
     * 取得当明选中下标
     *
     * @return
     */
    public int getCurIndex() {
        return curIndex;
    }

    /**
     * 停止滚动
     */
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 开始滚动
     */
    public void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        MyImgScroll.this.setCurrentItem(MyImgScroll.this
                                .getCurrentItem() + 1);
                    }
                });
            }
        }, mScrollTime, mScrollTime);
    }

    // 适配器 //循环设置
    private class MyPagerAdapter extends PagerAdapter {
        public void finishUpdate(View arg0) {
        }

        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        public int getCount() {
            if (mListViews.size() == 1) {// 一张图片时不用流动
                return mListViews.size();
            }
            return Integer.MAX_VALUE;
        }

        public Object instantiateItem(View v, int i) {
            if (((ViewPager) v).getChildCount() == mListViews.size()) {
                ((ViewPager) v)
                        .removeView(mListViews.get(i % mListViews.size()));
            }
            ((ViewPager) v).addView(mListViews.get(i % mListViews.size()), 0);
            return mListViews.get(i % mListViews.size());
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        public Parcelable saveState() {
            return null;
        }

        public void startUpdate(View arg0) {
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
        }
    }

}
