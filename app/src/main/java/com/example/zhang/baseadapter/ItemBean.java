package com.example.zhang.baseadapter;


import java.io.Serializable;

/**
 * Created by zhang on 2017/3/28.
 */

public class ItemBean implements Serializable{
    //Serializable接口是启用其序列化功能的接口。实现java.io.Serializable 接口的类是可序列化的。
    // 没有实现此接口的类将不能使它们的任一状态被序列化或逆序列化。
    public String tv_title;
    public String tv_date;
    public String tv_cost;
    public  ItemBean(){

    }
    public ItemBean(String title,String date,String cost){
        this.tv_title=title;
        this.tv_date=date;
        this.tv_cost=cost;



    }
}
