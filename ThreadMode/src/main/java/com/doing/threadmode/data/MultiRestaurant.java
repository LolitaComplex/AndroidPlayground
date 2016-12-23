package com.doing.threadmode.data;


import android.text.TextUtils;
import android.util.Log;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class MultiRestaurant {

    private static final String TAG = "MultiRestaurant";

    private static final MultiRestaurant rest = new MultiRestaurant();

    private LinkedList<String> orders = new LinkedList<>();
    private LinkedList<String> cookies = new LinkedList<>();

    private Thread chief;
    private Thread server;

    ReentrantLock lockOrder = new ReentrantLock();
    ReentrantLock lockChief = new ReentrantLock();

    Condition conditionOrder = lockOrder.newCondition();
    Condition conditionChief = lockChief.newCondition();

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

        chief.interrupt();
        server.interrupt();

        return this;
    }

    public void openForBussiness() {
        if (open) {
            guestComming();
            startOrdering();
            startCooking();
            serving();
        }
    }


    private void guestComming() {
        Log.w(TAG, "guestComming: 客户来了");
    }

    private void startOrdering() {
        try {
            lockOrder.lock();
            orders.offer("订单1: 驴Xxx抄蒜苗");
            orders.offer("订单2: 西红柿炒蛋");

            Log.w(TAG, "startOrdering: 订单接受完毕" + orders.toString());
            conditionOrder.signal();
        } finally {
            lockOrder.unlock();
        }
    }

    private void startCooking() {
        if (chief == null || chief.isInterrupted()) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (chief.isInterrupted()) {
                            Log.w(TAG, "startCooking: 已经停止烹饪");
                            break;
                        }
                        try {
                            lockOrder.lock();
                            String order = "";

                            if (!orders.isEmpty()) {
                                while (!TextUtils.isEmpty(order = orders.poll())) {
                                    for (int i = 0; i < 1000; i++) {
                                        for (int j = 0; j < 1000; j++) {

                                        }
                                    }

                                    try {
                                        lockChief.lock();
                                        cookies.offer(order);
                                        Log.w(TAG, "startCooking: 已完成一道订单——" + order);
                                        conditionChief.signal();
                                    } finally {
                                        lockChief.unlock();
                                    }
                                }
                            } else {
                                try {
                                    conditionOrder.await();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } finally {
                            lockOrder.unlock();
                        }
                    }
                }
            };
            chief = new Thread(runnable);
            chief.start();
        }
    }

    private void serving() {

        if (server == null || server.isInterrupted()) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        try {
                            lockChief.lock();
                            if (!cookies.isEmpty()) {

                                if (server.isInterrupted()) {
                                    break;
                                }

                                for (int j = 0; j < 1000; j++) {

                                }

                                String cookie = "";
                                while (!TextUtils.isEmpty(cookie = cookies.poll())) {
                                    Log.w(TAG, "serving: 上餐：" + cookie + "，顾客开始食用");
                                }
                            } else {
                                conditionChief.await();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            lockChief.unlock();
                        }
                    }
                }
            };

            server = new Thread(runnable);
            server.start();
        }
    }
}
