package com.example.zhang.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhang on 2017/3/28.
 */

public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ItemBean>  list_item;

    public MyAdapter(Context context,List<ItemBean> list){
        this.list_item=list;
        inflater=LayoutInflater.from(context);
        //从一个Context中，获得一个布局填充器，这样你就可以使用这个填充器来把xml布局文件转为View对象了。

    }
    @Override
    public int getCount() {
        return list_item.size();
    }
    //返回list_item的数量

    @Override
    public Object getItem(int position) {
        return list_item.get(position);
    }
    //返回相应的list'中的玩意儿

    @Override
    public long getItemId(int position) {
        return position;
    }
    //返回位置


    //返回VIew 文艺式 减少的findview的 时间 并且不是每次的创建convertView（view）
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder;
        if(convertView==null){
            holder =new viewHolder();
            convertView= inflater.inflate(R.layout.lv_list,null);
            holder.texttitle= (TextView) convertView.findViewById(R.id.lv_title);
            holder.textdate= (TextView) convertView.findViewById(R.id.lv_date);
            holder.textcost= (TextView) convertView.findViewById(R.id.lv_cast);
            convertView.setTag(holder);
        }
        else{

            holder = (viewHolder) convertView.getTag();
        }
        holder.texttitle.setText(list_item.get(position).tv_title);
        holder.textdate.setText(list_item.get(position).tv_date);
        holder.textcost.setText(list_item.get(position).tv_cost);
        return convertView;

    }

    //优化findview

   class viewHolder {
         public TextView texttitle;
         public TextView textdate;
         public TextView textcost;


    }
}
