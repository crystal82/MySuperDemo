package com.hwq.ruminate.ipc_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.hwq.ruminate.ipc_service.aidl.Book;
import com.hwq.ruminate.ipc_service.aidl.IBookManager;
import com.hwq.ruminate.ipc_service.aidl.IReadBookListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过AIDL方式进行进程间的通讯
 */
public class AidlService extends Service {
    private static final String TAG = "AidlService";

    //声明
    private RemoteCallbackList<IReadBookListener> callbackList;
    private ArrayList<Book> bookList;

    public AidlService() {
        callbackList = new RemoteCallbackList<>();
        bookList = new ArrayList<>();
        bookList.add(new Book(1, "aa", 1));
        bookList.add(new Book(2, "bb", 2));
        bookList.add(new Book(3, "cc", 3));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBookManager;
    }

    private IBookManager.Stub iBookManager = new IBookManager.Stub() {

        @Override
        public long readBook(int id) throws RemoteException {

            for (Book book : bookList) {
                if (book.bookId == id) {
                    Log.d(TAG, "开始读书");
                    try {
                        Thread.sleep(book.readTime * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "读书完毕");

                    notifyAllCallback(book);
                    return book.readTime;
                }
            }
            return -1;
        }

        private void notifyAllCallback(Book book) throws RemoteException {
            int count = callbackList.beginBroadcast();
            for (int i = 0; i < count; i++) {
                IReadBookListener listener = callbackList.getBroadcastItem(i);
                if (listener != null) {
                    listener.onReadBookOver(book);
                }
            }
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
            for (Book book : bookList) {
                if (name.equals(book.bookName)) {
                    return book.bookId;
                }
            }
            return -1;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }
    };
}
