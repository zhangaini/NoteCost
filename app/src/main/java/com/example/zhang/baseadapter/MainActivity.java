package com.example.zhang.baseadapter;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ItemBean> list_;
    private ListView view_list;
    private Mydatabase myDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_=new ArrayList<ItemBean>();
        view_list= (ListView) findViewById(R.id.lv_1);
        myDatabase = new Mydatabase(this);
        final MyAdapter adapter = new MyAdapter(MainActivity.this,list_);
        view_list.setAdapter(adapter);
        //上面是设置适配器的内容  并加载到view_list里面
        initItemBean();
        //从数据库 读取内容

        //设置浮动按钮
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);//设置一个弹出窗口
                LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
                View dialog=inflater.inflate(R.layout.fabview,null);//将弹出窗口对应的布局文件加载到dialo里面 最后在把dialog放入弹出窗口
                final EditText et_title= (EditText) dialog.findViewById(R.id.ed_title);
                final  DatePicker dp_date= (DatePicker)dialog.findViewById(R.id.dp_date);
                final EditText et_cost= (EditText)  dialog.findViewById(R.id.ed_cost);//这里使用dialog  不是默认的this
                builder.setView(dialog);
                builder.setTitle("新增项");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    //弹出窗口的设置一个确定按键 并且确定时触发的事件
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ItemBean bean =new ItemBean();
                        bean.tv_title=et_title.getText().toString();
                        bean.tv_cost=et_cost.getText().toString();
                        bean.tv_date=dp_date.getYear()+"-"+(dp_date.getMonth()+1)+"-"+dp_date.getDayOfMonth();
                        myDatabase.insertBase(bean);//当我们把数据插入了之后并没有及时地去更新操作
                        list_.add(bean);    //放入list_以便后面的adapter 更新时候数据的更新
                        adapter.notifyDataSetChanged();//我们是在设置适配器的时候更新的 那么把适配器的内容notify就OK了

                    }
                });
                builder.create().show();//这句十分关键 不创建 show()是显示不出来的

            }

        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.values,menu);
        return true;
    }
    //重载menu布局

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {switch (item.getItemId()){
        case R.id.item1:
            //Toast.makeText(this,"你点击了add",Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(MainActivity.this,ChartView.class);
            //我们将传送一个可以序列化的数据过去，就是把list_传过去但是list——不是可序列化的 那么list 里面的itembean 就要实现序列化接口
           // Serializable接口是启用其序列化功能的接口。实现java.io.Serializable 接口
            // 的类是可序列化的。没有实现此接口的类将不能使它们的任一状态被序列化或逆序列化。
            intent.putExtra("list_",(Serializable) list_);
            startActivity(intent);

            break;
        case R.id.item2:
            Toast.makeText(this,"你点击了remove",Toast.LENGTH_SHORT).show();
            break;
        default:
    }
        return true;
    }

    private void initItemBean() {
        //从数据库读取文件
        Cursor c=myDatabase.getAllItem();
        if(c!=null)
        while (c.moveToNext()){
            ItemBean bean=new ItemBean();
            bean.tv_title=c.getString(c.getColumnIndex("title"));
            bean.tv_date=c.getString(c.getColumnIndex("date"));
            bean.tv_cost=c.getString(c.getColumnIndex("cost"));
            list_.add(bean);
        }
        c.close();//记住关闭

    }
}
