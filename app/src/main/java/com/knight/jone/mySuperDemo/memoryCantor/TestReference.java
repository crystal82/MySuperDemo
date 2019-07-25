package com.knight.jone.mySuperDemo.memoryCantor;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class TestReference {

    public static void main(String[] args) {
        Animal a1 = new Animal("a", 1);
        Animal a2 = new Animal("b", 2);
        //内存不够的时候GC会被回收
        SoftReference<Animal> softReference = new SoftReference<Animal>(a1);
        //弱引用，GC时候，如果包装对象已经回收，那么为null
        WeakReference<Animal> weakReference = new WeakReference<Animal>(a2);

        System.out.println(softReference.get());
        System.out.println(weakReference.get());
        a1=null;
        a2=null;

        System.gc();
        System.out.println(softReference.get());
        System.out.println(weakReference.get());//null

        ReferenceQueue referenceQueue = new ReferenceQueue();

    }
}
