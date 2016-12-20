package com.doing.threadmode.data;


import android.util.Log;

import java.util.LinkedList;

public class MultiRestaurant {

    private static final String TAG = "MultiRestaurant";

    private static final MultiRestaurant rest = new MultiRestaurant();

    private LinkedList<String> orders = new LinkedList<>();
    private LinkedList<String> cookies = new LinkedList<>();

    private Thread chief;
    private Thread server;

    private boolean open = false;


    private MultiRestaurant() {
    }

    public static MultiRestaurant getRestaurant() {
        return rest;
    }

    public MultiRestaurant open() {
        Log.w(TAG, "open: 饭店开门咯");
        open = true;
        return this;
    }

    public MultiRestaurant colse() {
        Log.w(TAG, "colse: 饭店关门了");
        open = false;
        return this;
    }

    public void openForBussiness() {
        if (open) {
            guestComming();
            startOrdering();
            startCooking();
        }
    }


    private void guestComming() {
        Log.w(TAG, "guestComming: 客户来了");
    }

    private void startOrdering() {
        orders.add("订单1: 驴Xxx抄蒜苗");
        orders.add("订单2: 西红柿炒蛋");

        Log.w(TAG, "startOrdering: 订单接受完毕" + orders.toString());
    }

    private void startCooking() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!orders.isEmpty()) {
                        for (String order = orders.poll(); order != null
                                ; order = orders.poll()) {
                            for (int i = 0; i < 1000; i++) {
                                for (int j = 0; j < 1000; j++) {

                                }
                            }
                            cookies.offer(order);
                            Log.w(TAG, "startCooking: 已完成一道订单——" + order);
                            serving();
                        }
                    }
                }
            }
        };

        chief = new Thread(runnable);
        chief.start();
    }

    private void serving() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 1000; j++) {

                }
                Log.w(TAG, "serving: 上餐：" + cookies.poll() + "，顾客开始食用");
            }
        };

        server = new Thread(runnable);
        server.start();
    }
}
