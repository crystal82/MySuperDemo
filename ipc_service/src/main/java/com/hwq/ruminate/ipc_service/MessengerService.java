package com.hwq.ruminate.ipc_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Messenger的方式进行进程间通讯
 *
 * 1.创建Handler用于创建Messenger对象
 * 2.Service的onBind返回Messenger中的对象Binder
 */
public class MessengerService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
