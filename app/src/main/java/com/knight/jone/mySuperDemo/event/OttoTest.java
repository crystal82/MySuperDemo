package com.knight.jone.mySuperDemo.event;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

public class OttoTest extends Bus {

    private volatile static OttoTest ottoTest;

    public class OttoMsg {
        String name;
    }

    public static OttoTest getInstance() {
        if (ottoTest == null) {
            synchronized (OttoTest.class) {
                if (ottoTest == null) {
                    ottoTest = new OttoTest();
                }
            }
        }

        return ottoTest;
    }

    public void registerTest(Object object) {
        OttoTest.getInstance().register(object);
    }

    public void unregisterTest(Object object) {
        OttoTest.getInstance().unregister(object);
    }

    public void postInfo(OttoMsg msg) {
        OttoTest.getInstance().post(msg);
    }

    @Produce
    public OttoMsg produceMsg() {
        return new OttoMsg();
    }

    @Subscribe
    public void msgBack(OttoMsg ottoMsg) {
        System.out.println(ottoMsg);
    }
}
