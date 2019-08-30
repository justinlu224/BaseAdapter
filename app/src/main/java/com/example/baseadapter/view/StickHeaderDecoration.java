package com.example.baseadapter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maobc.customer.R;
import com.maobc.customer.adapter.SearchCityAdapter;
import com.maobc.customer.utils.LogUtlis;

//RecyclerView 繪製stickHeader 固定在上方
public class StickHeaderDecoration extends RecyclerView.ItemDecoration {
    private final String TAG = StickHeaderDecoration.class.getCanonicalName();
    private int mItemHeaderHeight;
    private int mTextPaddingLeft;

    //底色
    private Paint mItemHeaderPaint;
    private Paint mTextPaint;
    private Paint mLinePaint;

    private Rect mTextRect;

    public StickHeaderDecoration(Context context){
        mItemHeaderHeight = context.getResources().getDimensionPixelOffset(R.dimen.header_hight);
        mTextPaddingLeft = dp2px(context,6);

        mTextRect = new Rect();

        mItemHeaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mItemHeaderPaint.setColor(context.getResources().getColor(R.color.color_area_stick_header));

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(context.getResources().getColor(R.color.color_white));

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(context.getResources().getColor(R.color.color_line));
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getAdapter() instanceof SearchCityAdapter) {
            SearchCityAdapter adapter = (SearchCityAdapter) parent.getAdapter();
            int count = parent.getChildCount();//获取可见范围内Item的总数

            for (int i = 0; i < count; i++) {
                View view = parent.getChildAt(i);
                int position = parent.getChildLayoutPosition(view)-1;
//                LogUtlis.d(TAG,178,"onDraw,position: "+position);
                boolean isHeader = adapter.isItemHeader(position);
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();

//                boolean isHeaderView = adapter.isHeaderView(position);
//                if (isHeaderView){
//                    return;
//                }

                if (isHeader) {
//                    LogUtlis.d(TAG,58,"position: "+position);
                    if ( adapter.getGroupName(position) == null){
                        return;
                    }
                    c.drawRect(left, view.getTop() - mItemHeaderHeight, right, view.getTop(), mItemHeaderPaint);
                    mTextPaint.getTextBounds(adapter.getGroupName(position), 0, adapter.getGroupName(position).length(), mTextRect);
                    c.drawText(adapter.getGroupName(position), left + mTextPaddingLeft, (view.getTop() - mItemHeaderHeight) + mItemHeaderHeight / 2 + mTextRect.height() / 2, mTextPaint);
                } else {
                    c.drawRect(left, view.getTop() - 1, right, view.getTop(), mLinePaint);
                }
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getAdapter() instanceof SearchCityAdapter) {
            SearchCityAdapter adapter = (SearchCityAdapter) parent.getAdapter();
            int position = (((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition());
//            LogUtlis.d(TAG,208,"onDrawOver,position: "+position);

            View view = parent.findViewHolderForAdapterPosition(position).itemView;
            boolean isHeader = adapter.isItemHeader(position );
            int top = parent.getPaddingTop();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            boolean isHeaderView = adapter.isHeaderView(position);
            if (isHeaderView){
                return;
            }

            if ( adapter.getGroupName(position) == null){
                return;
            }
            if (isHeader) {
                int bottom = Math.min(mItemHeaderHeight, view.getBottom());
                c.drawRect(left, top + view.getTop() - mItemHeaderHeight, right, top + bottom, mItemHeaderPaint);
                mTextPaint.getTextBounds(adapter.getGroupName(position-1), 0, adapter.getGroupName(position-1).length(), mTextRect);
                LogUtlis.d(TAG,229,"getGroupName:　"+adapter.getGroupName(position-1));
                c.drawText(adapter.getGroupName(position), left + mTextPaddingLeft, top + mItemHeaderHeight / 2 + mTextRect.height() / 2 - (mItemHeaderHeight - bottom), mTextPaint);
            } else {
                c.drawRect(left, top, right, top + mItemHeaderHeight, mItemHeaderPaint);
                mTextPaint.getTextBounds(adapter.getGroupName(position), 0, adapter.getGroupName(position).length(), mTextRect);
                LogUtlis.d(TAG,234,"getGroupName:　"+adapter.getGroupName(position));
                c.drawText(adapter.getGroupName(position), left + mTextPaddingLeft, top + mItemHeaderHeight / 2 + mTextRect.height() / 2, mTextPaint);
            }
            c.save();
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getAdapter() instanceof SearchCityAdapter) {
            SearchCityAdapter adapter = (SearchCityAdapter) parent.getAdapter();
            int position = parent.getChildLayoutPosition(view)-1;
            boolean isHeader = adapter.isItemHeader(position);
//            LogUtlis.d(TAG,244,"getItemOffsets,position: "+position);
//            boolean isHeaderView = adapter.isHeaderView(position);
//            if (isHeaderView){
//                return;
//            }

            if (isHeader) {
                outRect.top = mItemHeaderHeight;
            } else {
                outRect.top = 1;
            }
        }
    }

    private int dp2px(Context context, float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
