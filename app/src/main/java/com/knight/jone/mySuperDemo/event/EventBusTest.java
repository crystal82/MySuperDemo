package com.knight.jone.mySuperDemo.event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusTest {

    public class MessageEvent {
    }

    public void prepare(boolean isRegist) {
        if (isRegist) {
            EventBus.getDefault().register(this);
        } else {
            EventBus.getDefault().unregister(this);
        }
    }

    public void test() {
        EventBus aDefault = EventBus.getDefault();
        aDefault.post(new MessageEvent());
    }

    public void postStickEvent(){
        EventBus.getDefault().postSticky(new MessageEvent());
    }
    /**
     * 支持粘性事件
     * 发送事件之后，再订阅也能收到该事件，类似粘性广播。
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void stickEvent(){

    }


    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void despose(MessageEvent messageEvent) {

    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void despose() {

    }
}
