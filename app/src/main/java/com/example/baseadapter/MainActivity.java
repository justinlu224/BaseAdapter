package com.example.baseadapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.baseadapter.view.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseAdapter.OnItemClickListener<CityListData> {

    List<CityListData>cityListDataList = new ArrayList<>();
    MyAdapter myAdapter;
    LinearLayoutManager layoutManager;
    private LayoutInflater layoutInflater;
    RecyclerView recyclerView;
    private LoadingDialog loadingDialog;
    private RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initAdapter();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        requestManager = Glide.with(this);
        loadingDialog = new LoadingDialog(this,requestManager);
        loadingDialog.setTitle("Loading...");
        loadingDialog.show();
    }

    private void initAdapter() {
        layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutInflater = LayoutInflater.from(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(cityListDataList);
        View footer = layoutInflater.inflate(R.layout.area_footer, recyclerView, false);
        myAdapter.setFooterView(footer);
        myAdapter.addDatas(cityListDataList);
        myAdapter.setOnItemClickLister(this);
        recyclerView.setAdapter(myAdapter);
    }


    private void initData(){
        CityListData cityListData = new CityListData();
        cityListData.setName("123");
        cityListData.setGroupName("99999");
        CityListData cityListData1 = new CityListData();
        cityListData1.setName("1231");
        cityListData1.setGroupName("999991");
        CityListData cityListData2 = new CityListData();
        cityListData2.setName("1232");
        cityListData2.setGroupName("999992");
        CityListData cityListData3 = new CityListData();
        cityListData3.setName("1233");
        cityListData3.setGroupName("999993");
        cityListDataList.add(cityListData);
        cityListDataList.add(cityListData1);
        cityListDataList.add(cityListData2);
        cityListDataList.add(cityListData3);
        cityListDataList.add(cityListData);
        cityListDataList.add(cityListData);
        cityListDataList.add(cityListData1);
        cityListDataList.add(cityListData2);
        cityListDataList.add(cityListData3);
        cityListDataList.add(cityListData);

    }


    @Override
    public void onItemClick(int position, CityListData data) {
        Toast.makeText(this, data.getName(), Toast.LENGTH_SHORT).show();
    }
}
