package com.hwq.ruminate.pic_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.hwq.ruminate.ipc_client.R;

/**
 * 通过Messenger方式进行进程间的通信
 *
 * @author zone
 */
public class MessengerActivity extends AppCompatActivity implements Handler.Callback {

    private static final String TAG = "MessengerActivity";
    private EditText etSendMessage;
    private Messenger messenger;
    private Handler messageHandler = new Handler(this);
    private Messenger replayMessenger = new Messenger(messageHandler);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        etSendMessage = findViewById(R.id.et_send_message);
        initListener();
    }

    ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };

    private void initListener() {
        findViewById(R.id.bind_messenger_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "bind_messenger_service");

                Intent intent = new Intent();
                intent.setClassName("com.hwq.ruminate.ipc_service", "com.hwq.ruminate.ipc_service.MessengerService");
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });
        findViewById(R.id.btn_send_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (messenger != null) {
                    //准备数据
                    Message message = Message.obtain(null, 100);
                    Bundle bundle = new Bundle();
                    bundle.putString("messenger_client_info", etSendMessage.getText().toString());
                    message.setData(bundle);

                    //设置服务器回复的Messenger，不设置服务器"message.replyTo == null"
                    message.replyTo = replayMessenger;

                    //发送数据
                    try {
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(serviceConnection);
    }

    /**
     * @param msg A {@link Message Message} object
     * @return True if no further handling is desired
     */
    @Override
    public boolean handleMessage(Message msg) {
        Log.d(TAG, "ServiceBack:" + msg.what + "  " + msg.getData().get("messenger_service_back"));
        return false;
    }
}
