package com.example.baseadapter.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.example.baseadapter.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;


import java.text.DecimalFormat;
import java.util.List;

/***
 * MPAndroidChart
 * 曲線圖點擊產生的markerView
 */
public class ChartMarkerView extends MarkerView {
    private TextView tvDate;
    private TextView tvValue0;
    private TextView tvValue1;
    private IAxisValueFormatter xAxisValueFormatter;
    DecimalFormat df = new DecimalFormat("0.00");

    public ChartMarkerView(Context context, IAxisValueFormatter xAxisValueFormatter) {
        super(context, R.layout.layout_marker_view);
        this.xAxisValueFormatter = xAxisValueFormatter;
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvValue0 = (TextView) findViewById(R.id.tv_value0);
        tvValue1 = (TextView) findViewById(R.id.tv_value1);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        Chart chart = getChartView();
        if (chart instanceof LineChart) {
            LineData lineData = ((LineChart) chart).getLineData();
            //取得圖表所有曲線
            List<ILineDataSet> dataSetList = lineData.getDataSets();
            for (int i = 0; i < dataSetList.size(); i++) {
                LineDataSet dataSet = (LineDataSet) dataSetList.get(i);
                //取得曲線的所有在Y軸的數據,根據當前x軸的位置來獲取對應y軸值
                float y = dataSet.getValues().get((int) e.getX()).getY();
                if (i == 0) {
                    tvValue1.setText(dataSet.getLabel() + "：" + y + "%");
                }
                if (i == 1) {
                    tvValue0.setText(dataSet.getLabel() + "：" + y + "%");
                }
            }
            tvDate.setText(xAxisValueFormatter.getFormattedValue(e.getX(), null));
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }



}
