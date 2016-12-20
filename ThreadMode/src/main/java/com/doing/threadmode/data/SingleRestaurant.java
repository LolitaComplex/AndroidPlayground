package com.doing.threadmode.data;


import android.util.Log;

import java.util.LinkedList;

public class SingleRestaurant {

    private static final String TAG = "SingleRestaurant";

    private static final SingleRestaurant rest = new SingleRestaurant();

    private LinkedList<String> orders = new LinkedList<>();
    private LinkedList<String> cookies = new LinkedList<>();

    private boolean open = false;


    private SingleRestaurant(){}

    public static SingleRestaurant getRestaurant() {
        return rest;
    }

    public SingleRestaurant open() {
        Log.i(TAG, "open: 饭店开门咯");
        open = true;
        return this;
    }

    public SingleRestaurant colse() {
        Log.i(TAG, "colse: 饭店关门了");
        open = false;
        return this;
    }

    public void openForBussiness() {
        guestComming();
        startOrdering();
        startCooking();
    }

    private void guestComming() {
        Log.i(TAG, "guestComming: 客户来了");
    }

    private void startOrdering() {
        orders.add("订单1: 驴Xxx抄蒜苗");
        orders.add("订单2: 西红柿炒蛋");

        Log.i(TAG, "startOrdering: 订单接受完毕" + orders.toString());
    }

    private void startCooking() {
        for(String order = orders.poll(); order != null
                ; order = orders.poll()) {
            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 1000; j++) {

                }
            }
            cookies.offer(order);
            Log.i(TAG, "startCooking: 已完成一道订单——" + order);
            serving();
        }
    }

    private void serving() {
        Log.i(TAG, "serving: 上餐：" + cookies.poll() + "，顾客开始食用");
    }

}
