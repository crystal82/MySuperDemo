package com.knight.jone.mySuperDemo.memoryCantor;

public class JmmRuler {
    public static void main(String[] args) {
        ReorderExample reorderExample = new ReorderExample();
        new Thread(new Runnable() {
            @Override
            public void run() {
                reorderExample.writer();
            }
        }).start();

        //new Thread(new Runnable() {
        //    @Override
        //    public void run() {
        //        reorderExample.reader();
        //    }
        //}).start();

        reorderExample.rFlag();
        System.out.println(reorderExample.toString());
    }
}

/**
 * 重排序测试
 */
class ReorderExample {
    int a = 0;
    boolean flag = false;
    boolean rootReder = true;

    public void writer() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        a = 1;                   //1
        flag = true;             //2

        System.out.println("writer");
    }

    public void reader() {
        while (rootReder) {
            //System.out.println("reder flag" + flag);
            if (flag) {                //3
                int i = a * a;        //4
                System.out.println("reader:" + i);
                rootReder = false;
            }
        }
        System.out.println("End reader");
    }

    public void rFlag() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (rootReder) {
                    System.out.println("End reader:" + ((flag || a != 0)));
                    if ((flag || a != 0)) {
                        System.out.println("state:" + flag + "   a:" + a);
                        rootReder = false;
                    }
                }
            }
        }).start();
    }

    @Override
    public String toString() {
        return "ReorderExample{" +
                "a=" + a +
                ", flag=" + flag +
                '}';
    }
}
