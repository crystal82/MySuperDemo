package com.hwq.ruminate.pic_client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hwq.ruminate.ipc_client.R;
import com.hwq.ruminate.ipc_service.aidl.Book;
import com.hwq.ruminate.ipc_service.aidl.IBookManager;
import com.hwq.ruminate.ipc_service.aidl.IReadBookListener;

import java.util.List;

public class AidlActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AidlActivity";
    private Button bind_service;
    private Button register_listener;
    private Button unregister_listener;
    private Button book_list;
    private Button read_book;
    private EditText et_book_id;
    private IBookManager iBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bind_service = findViewById(R.id.bind_service);
        register_listener = findViewById(R.id.register_listener);
        unregister_listener = findViewById(R.id.unregister_listener);
        et_book_id = findViewById(R.id.et_book_id);
        book_list = findViewById(R.id.book_list);
        read_book = findViewById(R.id.read_book);
        bind_service.setOnClickListener(this);
        register_listener.setOnClickListener(this);
        unregister_listener.setOnClickListener(this);
        book_list.setOnClickListener(this);
        read_book.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(serviceConnection);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected");
            if(iBinder == null){
                Toast.makeText(AidlActivity.this, "权限不通过", Toast.LENGTH_SHORT).show();
            }
            iBookManager = IBookManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setClassName("com.hwq.ruminate.ipc_service", "com.hwq.ruminate.ipc_service.AidlService");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_service:
                Log.d(TAG, "----pid=" + android.os.Process.myPid());
                Log.d(TAG, "----tid=" + android.os.Process.myTid());
                Log.d(TAG, "----uid=" + android.os.Process.myUid());
                Log.d(TAG, "----http.agent=" + System.getProperty("http.agent"));
                bindService();
                break;
            case R.id.register_listener:
                try {
                    iBookManager.registerReadBookListener(iReadBookListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.unregister_listener:
                try {
                    iBookManager.unRegisterReadBookListener(iReadBookListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.read_book:
                String s = et_book_id.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                try {
                    iBookManager.readBook(Integer.parseInt(s));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.book_list:
                try {
                    List<Book> bookList = iBookManager.getBookList();
                    System.out.println("bookList:" + bookList.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
        }
    }

    IReadBookListener iReadBookListener = new IReadBookListener.Stub() {
        @Override
        public void onReadBookOver(Book book) throws RemoteException {
            Log.d(TAG, "onReadBookOver:" + book);
        }
    };
}
