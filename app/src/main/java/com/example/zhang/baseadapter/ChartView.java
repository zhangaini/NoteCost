package com.example.zhang.baseadapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;


/**
 * Created by zhang on 2017/3/29.
 */

public class ChartView extends AppCompatActivity {
    private List<ItemBean> list_2;
    private LineChartData chartLine;
    private LineChartView chartView;
    private LinearLayout chartlayout;
    private Map<String,Float> table = new TreeMap<String,Float>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);//当点击菜单栏的查看图表的时候传递值过来
        chartlayout= (LinearLayout)findViewById(R.id.chartView_layout);//注意不是this .find
        list_2= (List<ItemBean>) getIntent().getSerializableExtra("list_");
        getAlldate(list_2);//将所有的数据转换成map的形式 以键值对来作为line的数据
        creatLineDate();  //创建图表
    }

    private void creatLineDate() {
        List<Line> lines =new ArrayList<Line>();//可以同时加载多条线
        List<PointValue> values_Point=new ArrayList<PointValue>();//点的数据几个
        int index=0;
        for(float value: table.values()){
            values_Point.add(new PointValue(index,value));//取出每个点
            index++;
        }
        Line line=new Line(values_Point);//将点加载到线中
        line.setColor(Color.BLUE);
        line.setShape(ValueShape.CIRCLE);
        lines.add(line);//线加载到线集合中
        chartLine=new LineChartData(lines);//将集合加载到linechartdate
        chartView=new LineChartView(ChartView.this);//将linedate数据加载到chartview中
        chartView.setLineChartData(chartLine);
        chartlayout.addView(chartView);//布局文件加载此view

       chartView.setInteractive(true);
       // Chart.setInteractive(boolean isInteractive);
      //  Chart.setZoomType(ZoomType zoomType);
        chartView.setZoomType(ZoomType.HORIZONTAL);
      //  Chart.setContainerScrollEnabled(boolean isEnabled, ContainerScrollType type);
        chartView.setContainerScrollEnabled(true, ContainerScrollType.VERTICAL);

        //这是曲线图的性质 我实在没搞懂是啥性质 设不设置都这样..




    }

    private void getAlldate(List<ItemBean> list_2) {
        if(list_2!=null){
            for (int i=0;i<list_2.size();i++) {
                ItemBean bean = list_2.get(i);

                String date = bean.tv_date;
                float cost = Float.parseFloat(bean.tv_cost);
                if (!list_2.contains(date)){

                    table.put(date,cost);
                }
                else{
                    float temp=  table.get(date);//map里面已经有这个日期的花费 取出来
                    table.put(date,temp+cost);//再将他们的花费累加

                }
            }
        }

    }


}
