package com.cjj.addlistdatalibrary;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.viewpagerlibrary.BaseFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.Urls;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 说明 下拉刷新界面的基类
 */
public abstract class BaseSwipeRefreshFragment<T>
        extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        OnItemClickListener, OnScrollListener {

    // 没有状态
    public static final int LISTVIEW_ACTION_NONE = -1;
    // 初始化时，加载缓存状态
    public static final int LISTVIEW_ACTION_INIT = 1;
    // 刷新状态，显示toast
    public static final int LISTVIEW_ACTION_REFRESH = 2;
    // 下拉到底部时，获取下一页的状态
    public static final int LISTVIEW_ACTION_SCROLL = 3;

    static final int STATE_NONE = -1;
    static final int STATE_LOADING = 0;
    static final int STATE_LOADED = 1;

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected ListView mListView;
    private View mFooterView;
    private CommonAdapter<T> mAdapter;

    private View mFooterProgressBar;
    private TextView mFooterTextView;

    // 当前加载状态
    private int mState = STATE_NONE;
    // UI状态
    private int mListViewAction = LISTVIEW_ACTION_NONE;

    // 当前数据状态，如果是已经全部加载，则不再执行滚动到底部就加载的情况
    private int dataState = LISTVIEW_ACTION_NONE;

    protected int mCurrentPage = 1;

    protected TipInfoLayout mTipInfo;

    private boolean isFrist = true;

    private List<T> datas = null;

    public void postDataProject(String method) {
        postDataProject(method, null);
    }

    public void postDataProject(String method, Map<String, String> map) {
        PostRequest request = OkGo.post(Urls.formUrl(method))//
                .tag(this)// 请求的 tag,主要用于取消对应的请求
                .cacheMode(CacheMode.NO_CACHE);//缓存模式，详细请看缓存介绍
        dataProject(request, map);
    }

    public void getDataProject(String method) {
        getDataProject(method, null);
    }

    public void getDataProject(String method, Map<String, String> map) {
        GetRequest request = OkGo.get(Urls.formUrl(method))//
                .tag(this)// 请求的 tag,主要用于取消对应的请求
                .cacheMode(CacheMode.NO_CACHE);//缓存模式，详细请看缓存介绍
        dataProject(request, map);
    }

    /**
     * 自带 page 分页
     *
     * @param request
     * @param map
     */
    private void dataProject(BaseRequest request, Map<String, String> map) {
        datas = null;
        if ((isFrist || mAdapter.getCount() == 0) && mTipInfo != null) {
            mTipInfo.setLoading();
        }
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                request.params(entry.getKey(), entry.getValue());
            }
        }
        request.params("page", mCurrentPage + "");
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(String json, Call call, Response response) {
                datas = getDatas(json);
                if (datas != null && datas.size() > 0) {
                    mTipInfo.setHiden();
                    mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                    loadDataSuccess(datas);
                    isFrist = false;
                    setSwipeRefreshLoadedState();
                } else {
                    mTipInfo.setEmptyData(getEmptyTip());
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                mTipInfo.setLoadError();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = getAdapter();
        requestData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFooterView = inflater.inflate(R.layout.listview_footer, null);
        mFooterProgressBar = mFooterView
                .findViewById(R.id.listview_foot_progress);
        mFooterTextView = (TextView) mFooterView
                .findViewById(R.id.listview_foot_more);
        System.out.println("isVisibleToUser:onCreateView");
        return inflater.inflate(R.layout.base_swiperefresh, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        System.out.println("isVisibleToUser:onViewCreated");
        initView(view);
        setupListView();
        // 正在刷新的状态
        if (mListViewAction == LISTVIEW_ACTION_REFRESH) {
            setSwipeRefreshLoadingState();
        }
    }

    private boolean init = false;

    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        mListView = (ListView) view.findViewById(R.id.listView);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.swiperefresh_color1,
                R.color.swiperefresh_color2, R.color.swiperefresh_color3,
                R.color.swiperefresh_color4);
        mTipInfo = (TipInfoLayout) view.findViewById(R.id.tip_info);
        mTipInfo.setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });
    }

    /**
     * 初始化ListView
     */
    protected void setupListView() {
        mListView.setOnScrollListener(this);
        mListView.setOnItemClickListener(this);
        mListView.addFooterView(mFooterView);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        requestData();
    }

    /**
     * 设置顶部正在加载的状态
     */
    void setSwipeRefreshLoadingState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            // 防止多次重复刷新
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    /**
     * 设置顶部加载完毕的状态
     */
    void setSwipeRefreshLoadedState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    /**
     * 设置底部已加载全部的状态
     */
    void setFooterFullState() {
        if (mFooterView != null) {
            mFooterProgressBar.setVisibility(View.GONE);
            mFooterTextView.setText(R.string.load_full);
        }
    }

    /**
     * 设置底部加载中的状态
     */
    void setFooterLoadingState() {
        if (mFooterView != null) {
            mFooterProgressBar.setVisibility(View.VISIBLE);
            mFooterTextView.setText(R.string.load_ing);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // 点击了底部
        if (view == mFooterView) {
            return;
        }
        T data = mAdapter.getItem(position);
        if (data == null) return;
        onItemClick(position, data);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
        if (dataState == MessageData.MESSAGE_STATE_FULL
                || dataState == MessageData.MESSAGE_STATE_EMPTY
                || mState == STATE_LOADING) {
            return;
        }
        // 判断是否滚动到底部
        boolean scrollEnd = false;
        try {
            if (view.getPositionForView(mFooterView) == view
                    .getLastVisiblePosition())
                scrollEnd = true;
        } catch (Exception e) {
            scrollEnd = false;
        }

        if (scrollEnd) {
            ++mCurrentPage;
            requestData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
    }

    public abstract CommonAdapter<T> getAdapter();

    public abstract List<T> getDatas(String responeString);

    public abstract void requestData();

    public abstract void onItemClick(int position, T data);

    public void loadDataSuccess(List<T> datas) {
        if (datas == null) return;
        if (datas.size() < 20) {
            dataState = MessageData.MESSAGE_STATE_FULL;
            setFooterFullState();
        }
        if (mCurrentPage == 1) {
            mAdapter.clear();
        }
        mAdapter.addItem(datas);
        // 整个列表为空
        if (mAdapter.getCount() == 0) {
            mSwipeRefreshLayout.setVisibility(View.GONE);
            mTipInfo.setVisibility(View.VISIBLE);
            mTipInfo.setEmptyData(getEmptyTip());
        }
    }

    protected String getEmptyTip() {
        return "暂无数据";
    }
}