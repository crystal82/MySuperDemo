package com.hwq.ruminate.ipc_service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.hwq.ruminate.ipc_service.aidl.Book;
import com.hwq.ruminate.ipc_service.aidl.IBookManager;
import com.hwq.ruminate.ipc_service.aidl.IReadBookListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通过AIDL方式进行进程间的通讯
 */
public class AidlService extends Service {
    private static final String TAG = "AidlService";

    /**
     * 直接使用ArrayList存在隐患
     * AIDL中传递的对象非原来的对象，直接按对象进行保存/移除，无法真正解除绑定。
     * RemoteCallbackList底层使用接口的bind对象来保存，在aidl中binder对象是统一的。
     */
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
        //int callingPid = Binder.getCallingPid();
        //int callingUid = Binder.getCallingUid();
        //Log.d(TAG, "callingPid:" + callingPid + "  callingUid:" + callingUid);
        //Log.d(TAG, "Pid:" + android.os.Process.myPid() + "  Uid:" + android.os.Process.myUid());
        //PackageManager packageManager = AidlService.this.getPackageManager();
        //String[] packages = getPackageManager().getPackagesForUid(callingUid);
        //int result = packageManager.checkPermission("com.ipc.permission.ACCESS_BOOK_SERVICE", packages[0]);
        //Log.d(TAG, "onBind: result:" + result + "   package:" + Arrays.toString(packages));
        //可以在此处进行权限校验，校验不通过直接返回null
        //if (!checkPermission()) {
        //    return null;
        //}

        int check = checkCallingOrSelfPermission("com.ipc.permission.ACCESS_BOOK_SERVICE");
        Log.d(TAG, "onBind check:" + check);
        return iBookManager;
    }

    private boolean checkPermission() {
        int check = checkCallingPermission("com.ipc.permission.ACCESS_BOOK_SERVICE");
        int check2 = checkCallingPermission("com.ipc.permission.TEST");
        Log.d(TAG, "checkPermission:" + check + "   check2" + check2);

        return check != PackageManager.PERMISSION_DENIED;
    }

    private IBookManager.Stub iBookManager = new IBookManager.Stub() {

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            //执行方法时候会走到这里，可以添加校验条件。
            //权限校验不通过返回false，表示失败
            int callingPid = getCallingPid();
            int callingUid = getCallingUid();
            int check = checkCallingPermission("com.ipc.permission.ACCESS_BOOK_SERVICE");
            int check2 = checkCallingPermission("com.ipc.permission.ACCESS_BOOK_SERVICE222");
            Log.d(TAG, "onTransact callingPid:" + callingPid + "  callingUid:" + callingUid + "  check:" + check + "  check2:" + check2);

            String[] packages = getPackageManager().getPackagesForUid(callingUid);
            if (packages != null && packages.length > 0) {
                Log.d(TAG, "onTransact: " + Arrays.toString(packages));
            }


            return super.onTransact(code, data, reply, flags);
        }

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
