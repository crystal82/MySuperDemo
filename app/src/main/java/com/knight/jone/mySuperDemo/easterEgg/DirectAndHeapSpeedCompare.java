package com.knight.jone.mySuperDemo.easterEgg;

import java.nio.ByteBuffer;

/**
 * "堆内内存"申请和"堆外内存"申请时间对比。
 */
public class DirectAndHeapSpeedCompare {

    public static void main(String[] args){
        directAndHeapSpeedCompare();
    }

    public static void directAndHeapSpeedCompare() {
        int length = 100000;
        directExecuteTime(length);
        heapExecuteTime(length);
    }

    private static void directExecuteTime(int length) {
        long startTime = System.currentTimeMillis();
        ByteBuffer[] byteBufferArray = new ByteBuffer[length];
        for (int i = 0; i < length; i++) {
            byteBufferArray[i] = ByteBuffer.allocateDirect(1024);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("创建" + length + "个DirectByteBuffer所消耗的时间：" + (endTime - startTime));
    }

    private static void heapExecuteTime(int length) {
        long startTime = System.currentTimeMillis();
        ByteBuffer[] byteBufferArray = new ByteBuffer[length];
        for (int i = 0; i < length; i++) {
            byteBufferArray[i] = ByteBuffer.allocate(1024);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("创建" + length + "个HeapByteBuffer所消耗的时间：" + (endTime - startTime));
    }
}
