package com.doing.threadmode.data;

import android.text.TextUtils;
import android.util.Log;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolRestaurant {

    private static final String TAG = "ThreadPoolRestaurant";

    private static final ThreadPoolRestaurant rest = new ThreadPoolRestaurant();

    private LinkedList<String> orders = new LinkedList<>();
    private LinkedList<String> cookies = new LinkedList<>();

//    private Thread chief;
//    private Thread server;

    private ExecutorService executorChief = Executors.newFixedThreadPool(3);
    private ExecutorService executorService = Executors.newFixedThreadPool(3);


    private ReentrantLock lockOrder = new ReentrantLock();
    private ReentrantLock lockChief = new ReentrantLock();

    private boolean open = false;

    private ThreadPoolRestaurant() {

    }

    public static ThreadPoolRestaurant getRestaurant() {
        return rest;
    }

    public ThreadPoolRestaurant open() {
        Log.w(TAG, "open: 饭店开门咯");
        open = true;
        return this;
    }

    public ThreadPoolRestaurant colse() {
        Log.w(TAG, "colse: 饭店关门了");
        open = false;

        executorChief.shutdown();
        executorService.shutdown();

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
        try {
            lockOrder.lock();
            orders.offer("订单1: 驴Xxx抄蒜苗");
            orders.offer("订单2: 西红柿炒蛋");

            Log.w(TAG, "startOrdering: 订单接受完毕" + orders.toString());
        } finally {
            lockOrder.unlock();
        }
    }

    private void startCooking() {
        if (!executorChief.isShutdown()) {
            Runnable cookieTask = new Runnable() {
                @Override
                public void run() {
                    try {
                        lockOrder.lock();
                        String order = "";
                        if (!orders.isEmpty()) {
                            while (!TextUtils.isEmpty(order = orders.poll())) {
                                for (int i = 0; i < 10000; i++) {
                                    for (int j = 0; j < 10000; j++) {

                                    }
                                }

                                try {
                                    lockChief.lock();
                                    cookies.offer(order);
                                    Log.w(TAG, "startCooking: 已完成一道订单——" + order);
                                    serving();
                                } finally {
                                    lockChief.unlock();
                                }
                            }
                        }
                    } finally {
                        lockOrder.unlock();
                    }
                }
            };

            executorChief.execute(cookieTask);
        }
    }

    private void serving() {
        if (!executorService.isShutdown()) {
            Runnable serviceTask = new Runnable() {
                @Override
                public void run() {
                    try {
                        lockChief.lock();
                        if (!cookies.isEmpty()) {


                            for (int j = 0; j < 10000; j++) {
                                for (int i = 0; i < 1000; i++) {

                                }
                            }

                            String cookie = "";
                            while (!TextUtils.isEmpty(cookie = cookies.poll())) {
                                Log.w(TAG, "serving: 上餐：" + cookie + "，顾客开始食用");
                            }
                        }
                    } finally {
                        lockChief.unlock();
                    }
                }
            };
            executorService.execute(serviceTask);
        }
    }
}
