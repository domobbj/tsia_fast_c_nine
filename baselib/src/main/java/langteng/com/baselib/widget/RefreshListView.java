package langteng.com.baselib.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import langteng.com.baselib.R;
import langteng.com.baselib.utils.DeviceUtils;


/**
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener {

    private int lastItem;
    private OnLoadMoreListener listener;
    private boolean enableScroll = true;
    private boolean isLoading = false;
    protected View footView;
    protected View footViewNoData; //无数据时的样式
    protected View footViewNoDataCustom; //无数据时的样式(非默认样式)
    private TextView footTitle;   //加载时footer的标题
    private RotateLoading rotateLoading;//加载时的loading动画
    private TextView noDataTitle; //无数据时的标题

    public void setNeedToLoadMore(boolean needToLoadMore) {
        this.needToLoadMore = needToLoadMore;
    }

    private boolean needToLoadMore = true; //默认加载更多为显示状态,当没有数据时隐藏,不会触发家在更多
    private boolean needFadeTip = false; //默认没有顶部渐隐tip提示
    private TextView tip; //顶部渐隐tip

    public RefreshListView(Context context) {
        super(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
        LayoutInflater inflater = LayoutInflater.from(context);
        footView = inflater.inflate(R.layout.footer, null);
        footTitle = (TextView) footView.findViewById(R.id.footer_load_more_tv);
        rotateLoading = (RotateLoading) footView.findViewById(R.id.rotateloading);

        footViewNoData = inflater.inflate(R.layout.footer_no_data, null);
        noDataTitle = (TextView) footViewNoData.findViewById(R.id.no_data_title);
        reset();
        isLoading = true;
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (!enableScroll) {
            int expandSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                if (!isLoading && needToLoadMore) {
                    isLoading = true;
                    rotateLoading.setVisibility(View.VISIBLE);
                    listener.onLoadMore();
                    footTitle.setText("加载中......");
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        lastItem = firstVisibleItem + visibleItemCount - 1;
        if (totalItemCount > visibleItemCount && footView != null) {
            footView.setVisibility(View.VISIBLE);
        } else {
//            if (footView != null) {
//                removeFooterView(footView);
//            }
        }
        if (totalItemCount > visibleItemCount && footViewNoData != null) {
            footViewNoData.setVisibility(View.VISIBLE);
        } else {
//            if (footViewNoData != null) {
//                footViewNoData.setVisibility(View.GONE);
//                removeFooterView(footViewNoData);
//            }
        }

        if (needFadeTip && getScrollHight(0) >= DeviceUtils.getScreenHeight()) {
            tip.setVisibility(View.GONE);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.listener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    //设置顶部渐隐tip
    public void setFadeTip(TextView tip) {
        this.tip = tip;
        needFadeTip = true;
    }

    private int getScrollHight(int pos) {
        View c = this.getChildAt(pos);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = this.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }

    public void reset() {
        endLoadMore();
        needToLoadMore = true;
        if (!rotateLoading.isStart()) {
            rotateLoading.setVisibility(View.GONE);
            rotateLoading.start();
        }
        if (footViewNoDataCustom != null) {
            removeFooterView(footViewNoDataCustom);
        }
        if (getFooterViewsCount() == 0) {
            setFootVisible(true);
        }
        footTitle.setText("点击加载更多");
    }

    //设置footer显示隐藏及文字
    public void setFootVisible(boolean visible) {
        needToLoadMore = visible;
        if (visible) {
            addFooterView(footView);
            footView.setVisibility(VISIBLE);
        } else {
            footView.setVisibility(GONE);
            removeFooterView(footView);
            removeFooterView(footViewNoData);
        }
    }

    //listView 中没有任何数据,显示无数据的footer
    public void noData(String hint) {
        removeFooterView(footView);
        removeFooterView(footViewNoData);
        needToLoadMore = false;
        addFooterView(footViewNoData);
        noDataTitle.setText(hint);
    }

    //listView 中没有任何数据,显示无数据的footer
    public void noData(String hint, boolean isNeedload) {
        removeFooterView(footView);
        removeFooterView(footViewNoData);
        needToLoadMore = isNeedload;
        needToLoadMore = true;
        isLoading = false;
        addFooterView(footViewNoData);
        noDataTitle.setText(hint);
    }

    //listView 中没有任何数据,显示无数据的自定义footer,自定义
    public void noData(View v) {
        removeFooterView(footView);
        if (footViewNoDataCustom != null) {
            removeFooterView(footViewNoDataCustom);
        }
        footViewNoDataCustom = v;
        addFooterView(footViewNoDataCustom);
        needToLoadMore = false;
    }

    //内容全部加载完毕 (自定义hint)
    public void noMoreData(String hint) {
        if (rotateLoading.isStart()) {
            rotateLoading.stop();
            rotateLoading.setVisibility(GONE);
        }
        removeFooterView(footView);
        removeFooterView(footViewNoData);
        if (footViewNoDataCustom != null) {
            removeFooterView(footViewNoDataCustom);
        }
        addFooterView(footView);
        isLoading = false;
        needToLoadMore = false;//不需要加载更多
        footTitle.setText(hint); //加载中和加载完毕的title
    }

    //默认hint:没有更多了
    public void noMoreData() {
        noMoreData("没有更多了");
    }


    //结束load之后才可以触发新的load
    public void endLoadMore() {
        isLoading = false;
        rotateLoading.setVisibility(View.GONE);
        footTitle.setText("点击加载更多");
    }

    //是否允许自身滚动,如果嵌套在scrollview中使用时需要设为false
    public void setEnableScroll(boolean enableScroll) {
        this.enableScroll = enableScroll;
    }
}
