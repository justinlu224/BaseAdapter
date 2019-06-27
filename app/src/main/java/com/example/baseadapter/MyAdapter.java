package com.example.baseadapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter<CityListData> {
    private List<CityListData> cityListDataList;

    public MyAdapter(){

    }

    public MyAdapter(List<CityListData>cityListDataList){
        this.cityListDataList = cityListDataList;

    }

    @Override
    public void addDatas(List<CityListData> datas) {
        super.addDatas(datas);
    }

    @Override
    public void setFooterView(View footerView) {
        super.setFooterView(footerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area,parent,false);
        return new MyHolder(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, CityListData data) {
        if (viewHolder instanceof MyHolder){
            Log.d("123",data.getName());
            ((MyHolder) viewHolder).tvName.setText(data.getName());
            ((MyHolder) viewHolder).tvArea.setText(data.getGroupName());
        }
    }
    class MyHolder extends BaseAdapter.Holder {
        TextView tvName, tvArea;
        public MyHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvArea = itemView.findViewById(R.id.tvArea);
        }
    }
}
