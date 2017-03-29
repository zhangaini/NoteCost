package com.example.zhang.baseadapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhang on 2017/3/28.
 */

public class Mydatabase extends SQLiteOpenHelper {
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String COST = "cost";

    //将用到的数 右键 refactor  extract->constnnt 变为上面的类型 易于维护

    public Mydatabase(Context context) {
        super(context, "Mybase", null, 1);
    }
    //构造方法

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists MyCostTB (" +
                "id integer primary key," +
                "title varchar," +
                "date varchar," +
                "cost varchar)");

    }
    public void insertBase(ItemBean bean){
        SQLiteDatabase my_base=getWritableDatabase();
        ContentValues values_content =new ContentValues();
        values_content.put(TITLE,bean.tv_title);
        values_content.put(DATE,bean.tv_date);
        values_content.put(COST,bean.tv_cost);

        my_base.insert("MyCostTB",null,values_content);



    }
    public Cursor getAllItem(){
        SQLiteDatabase my_base2=getWritableDatabase();
   //   return  my_base2.rawQuery("select * from MyCostTB",null);
       return  my_base2.query("MyCostTB",null,null,null,null,null,DATE+" ASC");//按照时间排序 空格别忘记了

    }
    public void deleteBase(){
        SQLiteDatabase base=getWritableDatabase();
        base.delete("MyCostTB",null,null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
