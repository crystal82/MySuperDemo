package com.hwq.ruminate.ipc_service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Messenger的方式进行进程间通讯
 * <p>
 * 1.创建Handler用于创建Messenger对象
 * 2.Service的onBind返回Messenger中的对象Binder
 *
 * @author zone
 */
public class MessengerService extends Service implements Handler.Callback {

    private static final String TAG = "MessengerService";
    private Handler messageHandler = new Handler(this);
    public static int MESSENGER_SERVICE_CALL = 100;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(messageHandler).getBinder();
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        Log.d(TAG, "handleMessage message:" + message.what);
        if (message.what == MESSENGER_SERVICE_CALL) {
            //读取消息
            Log.d(TAG, "Client message is:" + message.getData().get("messenger_client_info"));

            //获取客户端绑定的回复Message，需要客户端执行"message.replyTo"设置独立的回复message，否则会有空指针异常。
            Messenger replyTo = message.replyTo;

            //准备回复数据
            Message backMessage = Message.obtain(null, 200);
            Bundle bundle = new Bundle();
            bundle.putString("messenger_service_back", "I get client message");
            backMessage.setData(bundle);

            try {
                replyTo.send(backMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
