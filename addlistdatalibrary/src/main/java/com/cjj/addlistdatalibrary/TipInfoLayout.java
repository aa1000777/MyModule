package com.cjj.addlistdatalibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cjj.base.StringUtils;


/**
 * 一些提示信息显示，包含有加载过程的显示
 * <p/>
 * Created by 火蚁 on 15/4/16.
 */
public class TipInfoLayout extends FrameLayout {

    private String netWorkError = "轻触重新加载";
    private String empty = "暂无数据";

    private ProgressBar mPbProgressBar;

    private View mTipContainer;

    private ImageView mTvTipState;

    private TextView mTvTipMsg;

    public TipInfoLayout(Context context) {
        super(context);
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.tip_info_layout, null, false);
        mPbProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading);
        mTvTipState = (ImageView) view.findViewById(R.id.tv_tip_state);
        mTvTipMsg = (TextView) view.findViewById(R.id.tv_tip_msg);
        mTipContainer = view.findViewById(R.id.ll_tip);
        setLoading();
        addView(view);
    }

    public void setOnClick(OnClickListener onClik) {
        this.setOnClickListener(onClik);
    }

    public void setHiden() {
        this.setVisibility(View.GONE);
    }

    public void setLoading() {
        this.setVisibility(VISIBLE);
        this.mPbProgressBar.setVisibility(View.VISIBLE);
        this.mTipContainer.setVisibility(View.GONE);
    }

    public void setLoadError() {
        setLoadError("");
    }

    public void setLoadError(String errorTip) {
        String tip = netWorkError;
        if (errorTip != null && !StringUtils.isEmpty(errorTip))
            tip = errorTip;
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTipContainer.setVisibility(View.VISIBLE);
        this.mTvTipState.setImageResource(R.drawable.page_icon_network);
        this.mTvTipMsg.setText(tip);
    }

    public void setEmptyData(String emptyTip) {
        this.setVisibility(VISIBLE);
        String tip = empty;
        if (emptyTip != null && !StringUtils.isEmpty(emptyTip))
            tip = emptyTip;
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTipContainer.setVisibility(View.VISIBLE);
        this.mTvTipState.setImageResource(R.drawable.pagefailed_bg);
        this.mTvTipMsg.setText(tip);
    }

}
