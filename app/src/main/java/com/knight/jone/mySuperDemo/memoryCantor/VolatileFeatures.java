package com.knight.jone.mySuperDemo.memoryCantor;


public class VolatileFeatures {
    public static void main(String[] args) {

    }
}

class VolatileFeaturesExample {
    //使用volatile声明64位的long型变量
    volatile long vl = 0L;
    public void set(long l) {
        //单个volatile变量的写
        vl = l;
    }
    public void getAndIncrement() {
        //复合（多个）volatile变量的读/写
        vl++;
    }
    public long get() {
        //单个volatile变量的读
        return vl;
    }
}

class VolatileFeaturesExample2 {
    // 64位的long型普通变量
    long vl = 0L;
    //对单个的普通 变量的写用同一个锁同步
    public synchronized void set(long l) {
        vl = l;
    }
    //普通方法调用
    public void getAndIncrement() {
        //调用已同步的读方法
        long temp = get();
        //普通写操作
        temp += 1L;
        //调用已同步的写方法
        set(temp);
    }

    public synchronized long get() {
        //对单个的普通变量的读用同一个锁同步
        return vl;
    }
}

