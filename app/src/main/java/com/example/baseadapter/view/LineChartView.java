package com.example.baseadapter.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.baseadapter.R;
import com.example.baseadapter.Utils.LogUtlis;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.List;

public class LineChartView implements OnChartValueSelectedListener {
    LineChart lineChart;
    View root;
    Context context;
   private int selectTab = 0;
   private float leftMinY = 0f, leftMaxY = 40f, rightMinY = -200f, rightMaxY = 900, monthMaxX = 13, monthMinX = 0, dayMaxX = 8, dayMin = 0;
   private List<Entry> orderList;
   private List<Entry> refundList;
   private List<HomeFragment.Temp>temps;


    public int getSelectTab() {
        return selectTab;
    }

    public void setSelectTab(int selectTab) {
        this.selectTab = selectTab;
    }

    public LineChartView(Context context, View root, LineChart lineChart, List<Entry> orderList, List<Entry> refundList, List<HomeFragment.Temp>temps) {
       this.lineChart = lineChart;
       this.root = root;
       this.context = context;
       this.orderList = orderList;
       this.refundList = refundList;
       this.temps = temps;
       initView();
    }

    public void initView(){
        lineChart = root.findViewById(R.id.chart);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.setDragDecelerationFrictionCoef(0.9f);

        lineChart.setDrawBorders(false);

        // enable scaling and dragging 設定滑動拖動

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately 控制縮放單獨x,y 或一起控制
        lineChart.setPinchZoom(true);

        // set an alternative background color
        lineChart.setBackgroundColor(Color.WHITE);

        // add data
        setData(orderList,refundList);
        LogUtlis.d("sele",66,"selsetab: "+selectTab+", getselect: "+getSelectTab());
//        setSelectTabStatus(getSelectTab());

        lineChart.animateX(1500);//設定x動畫

        // get the legend (only possible after setting data) 要再設置data之後才可以
        Legend l = lineChart.getLegend();

        // modify the legend ... 設置項目分類各項屬性
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        //設定x軸值
        XAxis xAxis = lineChart.getXAxis();
//        setDayStatus(selectTab,xAxis);
        xAxis.setTextSize(11f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        //自訂義x軸文字
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                LogUtlis.d("LineChart:",104,"position: "+(int) value % temps.size());
                    String va = temps.get((int) value % temps.size()).getVal();

                    return va;

            }
        });
        setMarkerView(xAxis);
        YAxis leftAxis = lineChart.getAxisLeft(); //左y
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(leftMaxY);//軸值
        leftAxis.setAxisMinimum(leftMinY);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = lineChart.getAxisRight(); //右y
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(rightMaxY);
        rightAxis.setAxisMinimum(rightMinY);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        rightAxis.setEnabled(false);
    }


    /**
     * 设置 可以显示X Y 轴自定义值的 MarkerView
     */
    public void setMarkerView(XAxis xAxis) {
        ChartMarkerView mv = new ChartMarkerView(context, xAxis.getValueFormatter());
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
        lineChart.invalidate();
    }



//    private void setData(int count, float range) {
//
//        //Entry = map的屬性 key,val  (x,y)值
//        ArrayList<Entry> values1 = new ArrayList<>();
//
//        for (int i = 0; i < count; i++) {
//            if (i == 0 |i == count-1){
//                continue;
//            }
//            float val = (float) (Math.random() * (range / 2f)) + 50;
//            values1.add(new Entry(i, val));
//        }
//
//        ArrayList<Entry> values2 = new ArrayList<>();
//
//        for (int i = 0; i < count; i++) {
//            if (i == 0 |i == count-1){
//                continue;
//            }
//            float val = (float) (Math.random() * range) + 450;
//            values2.add(new Entry(i, val));
//        }
//
//
//        LineDataSet set1, set2;
//
//        if (lineChart.getData() != null &&
//                lineChart.getData().getDataSetCount() > 0) {
//            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
//            set2 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
//
//            set1.setValues(values1);
//            set2.setValues(values2);
//
//            lineChart.getData().notifyDataChanged();
//            lineChart.notifyDataSetChanged();
//        } else {
//            // create a dataset and give it a type  LineDataSet: 線條物件
//            set1 = new LineDataSet(values1, "DataSet 1");
//
//            set1.setAxisDependency(YAxis.AxisDependency.LEFT);//通过setAxisDependency()来设置描述数据的坐标轴是使用左边Y轴还是右边Y轴
//            set1.setColor(context.getResources().getColor(R.color.main_shop_chartline_blue));//線的顏色
//            set1.setCircleColor(Color.BLACK);//節點顏色
//            set1.setLineWidth(2f);
//            set1.setCircleRadius(3f);//節點大小
//            set1.setFillAlpha(65);//設置填滿線區顏色的透明度
//            set1.setFillColor(ColorTemplate.getHoloBlue());
//            set1.setHighLightColor(Color.rgb(244, 117, 117));//點擊圖表出現的線顏色
//            set1.setDrawCircleHole(false);
//            //set1.setFillFormatter(new MyFillFormatter(0f));
//            //set1.setDrawHorizontalHighlightIndicator(false);
//            //set1.setVisible(false);
//            //set1.setCircleHoleColor(Color.WHITE);
//
//            // create a dataset and give it a type
//            set2 = new LineDataSet(values2, "DataSet 2");
//            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
//            set2.setColor(context.getResources().getColor(R.color.main_shop_chartline_yellow));
//            set2.setCircleColor(Color.BLACK);
//            set2.setLineWidth(2f);
//            set2.setCircleRadius(3f);
//            set2.setFillAlpha(65);
//            set2.setFillColor(Color.RED);
//            set2.setDrawCircleHole(false);
//            set2.setHighLightColor(Color.rgb(244, 117, 117));
////            set2.setFillFormatter(new MyFillFormatter(900f));
//
//            // create a data object with the data sets  LineData: 整個chart物件
//
//            LineData data = new LineData(set1, set2);
//            data.setValueTextColor(Color.BLACK);
//            data.setValueTextSize(9f);
//
//            // set data
//            lineChart.setData(data);
//        }
//    }
private void setData(List<Entry>values1, List<Entry>values2) {

    LineDataSet set1, set2;

    if (lineChart.getData() != null &&
            lineChart.getData().getDataSetCount() > 0) {
        set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
        set2 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);

        set1.setValues(values1);
        set2.setValues(values2);

        lineChart.getData().notifyDataChanged();
        lineChart.notifyDataSetChanged();
    } else {
        // create a dataset and give it a type  LineDataSet: 線條物件
        set1 = new LineDataSet(values1, "白癡計畫已退款筆數");

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);//通过setAxisDependency()来设置描述数据的坐标轴是使用左边Y轴还是右边Y轴
        set1.setColor(context.getResources().getColor(R.color.main_shop_chartline_blue));//線的顏色

        set1.setLineWidth(2f);
//
        set1.setFillAlpha(65);//設置填滿線區顏色的透明度
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));//點擊圖表出現的線顏色
        set1.setDrawCircleHole(false);
        //節點的設置
        set1.setDrawCircles(false);
//        set1.setCircleColor(Color.BLACK);//節點顏色
//        set1.setCircleRadius(3f);//節點大小
        //節點文字
//        set1.setDrawValues(false);

        //set1.setFillFormatter(new MyFillFormatter(0f));
        //set1.setDrawHorizontalHighlightIndicator(false);
        //set1.setVisible(false);
        //set1.setCircleHoleColor(Color.WHITE);

        // create a dataset and give it a type
        set2 = new LineDataSet(values2, "訂單筆數");
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(context.getResources().getColor(R.color.main_shop_chartline_yellow));
        set2.setLineWidth(2f);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.RED);
        set2.setDrawCircleHole(false);
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        //節點的設置
        set2.setDrawCircles(false);
//        set2.setCircleColor(Color.BLACK);//節點顏色
//        set2.setCircleRadius(3f);//節點大小
        //節點文字
//        set2.setDrawValues(false);
//            set2.setFillFormatter(new MyFillFormatter(900f));

        // create a data object with the data sets  LineData: 整個chart物件

        LineData data = new LineData(set1, set2);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(9f);

        // set data
        lineChart.setData(data);
    }
}

//    private void setSelectTabStatus(int select){
//        switch (select){
//            case 0:
//                setDayPerformance();
//                break;
//            case 1:
//                setWeekPerformance();
//                break;
//            case 2:
//
//                setMonthPerformance();
//                break;
//        }
//    }

    private void setDayStatus(int day, XAxis xAxis){
        switch (day){
            case 0:
                xAxis.setAxisMinimum(dayMin);
                xAxis.setAxisMaximum(dayMaxX);
                break;
            case 1:
                break;
            case 2:
                xAxis.setAxisMinimum(monthMinX);
                xAxis.setAxisMaximum(monthMaxX);
                break;
        }

    }

//    private void setDayPerformance(){
//        setData(9,8);
//    }
//
//    private void setMonthPerformance(){
//        setData(14,10);
//    }
//
//    private void setWeekPerformance(){
//        setData(6,5);
//    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        LogUtlis.d("Entry selected",229, e.toString());

        lineChart.centerViewToAnimated(e.getX(), e.getY(), lineChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);

    }

    @Override
    public void onNothingSelected() {

    }
}
