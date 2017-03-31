package com.doing.architectureencapsulation.listadapter.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-30.
 */

public class HeaderAndFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "HeaderAndFooterAdapter";

    private static final int HEADER_ITEM_TYPE = 1000;
    private static final int FOOTER_ITEM_TYPE = 2000;


    private final Adapter<RecyclerView.ViewHolder> mInnerAdapter;
    private SparseArray<View> mHeaderArray = new SparseArray<>();
    private SparseArray<View> mFooterArray = new SparseArray<>();

    public HeaderAndFooterAdapter(RecyclerView.Adapter adapter) {
        this.mInnerAdapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            Log.d(TAG, "getItemViewType: mHeaderArray");
            return mHeaderArray.keyAt(position);//O(1)
        }
        if (isFooterPosition(position)) {
            Log.d(TAG, "getItemViewType: mFooterArray");
            return mFooterArray.keyAt(position - mInnerAdapter.getItemCount() - mHeaderArray.size());
        }
        return mInnerAdapter.getItemViewType(position - mHeaderArray.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderArray.get(viewType) != null) {
            Log.d(TAG, "onCreateViewHolder: mHeaderArray");
            return BaseViewHolder.createViewHolder(parent.getContext(), mHeaderArray.get(viewType));//O(lnN)
        }

        if (mFooterArray.get(viewType) != null) {
            Log.d(TAG, "onCreateViewHolder: mFooterArray");
            return BaseViewHolder.createViewHolder(parent.getContext(), mFooterArray.get(viewType));
        }

        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - mHeaderArray.size());
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + mHeaderArray.size() + mFooterArray.size();
    }

    public void addHeaderView(View headerView) {
        mHeaderArray.put(HEADER_ITEM_TYPE + mHeaderArray.size(), headerView);
    }

    public void addFooterView(View footerView) {
        mFooterArray.put(FOOTER_ITEM_TYPE + mFooterArray.size(), footerView);
    }

    private boolean isHeaderPosition(int position) {
        return position < mHeaderArray.size();
    }

    private boolean isFooterPosition(int position) {
        int itemCount = mInnerAdapter.getItemCount() + mHeaderArray.size();
        return position >= itemCount;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookUp, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderArray.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFooterArray.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }

                if (oldLookUp != null) {
                    return oldLookUp.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        int position = holder.getLayoutPosition();
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }
}
