package com.doing.architectureencapsulation.listadapter.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-30.
 */

public class LoadMoreAdapter extends Adapter {

    private final Adapter<RecyclerView.ViewHolder> mInnerAdapter;

    public static final int LOADMORE_ITEM = 333333;

    private View mLoadMoreView;
    private int mLoadMoreViewId;
    private OnLoadMoreListener mLoadMoreListener;

    public LoadMoreAdapter(RecyclerView.Adapter adapter) {
        this.mInnerAdapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadMorePosition(position)){
            return LOADMORE_ITEM;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOADMORE_ITEM) {
            if (mLoadMoreViewId == 0) {
                return BaseViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else {
                return BaseViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreViewId);
            }
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isLoadMorePosition(position)) {
            if (mLoadMoreListener != null) {
                mLoadMoreListener.onLoadMore();
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + 1;
    }

    private boolean isLoadMorePosition(int position) {
        return (mLoadMoreView != null || mLoadMoreViewId == 0) && position >= mInnerAdapter.getItemCount();
    }

    public void setLoadMoreView(View loadMoreView) {
        this.mLoadMoreView = loadMoreView;
    }

    public void setLoadMoreView(@LayoutRes int res) {
        this.mLoadMoreViewId = res;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback()
        {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position)
            {
                if (isLoadMorePosition(position)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isLoadMorePosition(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }
}
