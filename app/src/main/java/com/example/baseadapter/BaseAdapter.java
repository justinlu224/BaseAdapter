package com.example.baseadapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View mFooterView;
    private View mHeaderView;
    private final int TYPE_FOOTER = 3;
    private final int TYPE_NORMAL = 4;
    private List<T> mDatas = new ArrayList<>();
    private int mPreLoadNumber;
    private RequestLoadMoreListener mRequestLoadMoreListener;

    private OnItemClickListener mListener;

    public void setOnItemClickLister(OnItemClickListener listener){
        this.mListener = listener;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public void addDatas(List<T>datas){
        mDatas.addAll(datas);
        notifyItemInserted(datas.size());
    }

    public View getFooterView(){
        return mFooterView;
    }

    public void setPreLoadNumber(int preLoadNumber) {
        if (preLoadNumber > 1) {
            mPreLoadNumber = preLoadNumber;
        }
    }

    public void setLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener){
        this.mRequestLoadMoreListener = requestLoadMoreListener;
    }

    //預載功能判斷用
    private void autoLoadMore(int position) {
        if (mRequestLoadMoreListener == null){
            return;
        }
        if (position < getItemCount() - mPreLoadNumber) {
            return;
        }
        mRequestLoadMoreListener.onLoadMoreRequested();
    }


    @Override
    public int getItemViewType(int position) {
        if ( mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new Holder(mFooterView);
        }
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_FOOTER) return;
        autoLoadMore(position);
        final int pos = getRealPosition(viewHolder);
        final T data = mDatas.get(pos);
        onBind(viewHolder, pos, data);

        if(mListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos, data);
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_FOOTER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (mHeaderView != null){
            return position -1;
        }
        return  position;
    }
    @Override
    public int getItemCount() {
        return mFooterView == null ? mDatas.size() : mDatas.size() + 1;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }
    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }
    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, final int viewType);
    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);
}
