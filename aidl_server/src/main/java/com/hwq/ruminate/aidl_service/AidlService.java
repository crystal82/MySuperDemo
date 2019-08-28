package com.hwq.ruminate.aidl_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.hwq.ruminate.aidl_service.aidl.Book;
import com.hwq.ruminate.aidl_service.aidl.IBookManager;
import com.hwq.ruminate.aidl_service.aidl.IReadBookListener;

import java.util.List;

public class AidlService extends Service {
    private static final String TAG = "AidlService";

    //声明
    private RemoteCallbackList<IReadBookListener> callbackList;

    public AidlService() {
        callbackList = new RemoteCallbackList<>();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBookManager;
    }

    private IBookManager.Stub iBookManager = new IBookManager.Stub() {

        @Override
        public long readBook(int id) throws RemoteException {


            return 0;
        }

        @Override
        public void registerReadBookListener(IReadBookListener listener) throws RemoteException {
            callbackList.register(listener);
            Log.d(TAG, "注册成功");
        }

        @Override
        public void unRegisterReadBookListener(IReadBookListener listener) throws RemoteException {
            callbackList.unregister(listener);
            Log.d(TAG, "注销成功");
        }

        @Override
        public int getBookId(String name) throws RemoteException {
            return 0;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return null;
        }
    };
}
