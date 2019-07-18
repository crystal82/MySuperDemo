//package com.knight.jone.mySuperDemo.socket;
//
//import android.support.annotation.Nullable;
//import android.support.annotation.WorkerThread;
//import android.util.Log;
//
//import com.mtlab.kiev.remote.bean.DataPacket;
//import com.mtlab.kiev.remote.datapacket.DataParser;
//import com.mtlab.kiev.remote.datapacket.MTDataPacketType;
//import com.mtlab.kiev.util.DataPackageUtil;
//import com.mtlab.kiev.util.Logger;
//import com.mtlab.kiev.util.ThreadManager;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.SocketAddress;
//import java.nio.ByteBuffer;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.concurrent.atomic.AtomicBoolean;
//
///**
// * tcp连接客户端
// *
// * @author hxx1@meitu.com on 2019/5/9
// */
//public class TcpSocketClient_IO {
//
//    private static final String TAG = "TcpSocketClient_IO";
//    private SocketConfig mSocketConfig;
//    private ITcpClientCallback mTcpClientCallback;
//    private SocketThread mSocketThread = null;
//    private ConnectState mConnectState = ConnectState.NONE;
//    private DataParser mDataParser;
//    private ByteBuffer mReceiveBuffer;
//    private ConcurrentLinkedQueue<ByteBuffer> mWriteBufferQueue = new ConcurrentLinkedQueue<>();
//
//    private ServerSocket mServerSocket;
//    private ReadThread mReadThread;
//    private Socket mSocket;
//
//
//    public TcpSocketClient_IO(SocketConfig socketConfig) {
//        this.mSocketConfig = socketConfig;
//        this.mReceiveBuffer = ByteBuffer.allocate(mSocketConfig.getPieceSize());
//        this.mDataParser = new DataParser();
//    }
//
//    /**
//     * 设置tcp客户端回调
//     *
//     * @param tcpClientCallback tcp客户端回调
//     */
//    public void setTcpClientCallback(ITcpClientCallback tcpClientCallback) {
//        this.mTcpClientCallback = tcpClientCallback;
//    }
//
//    /**
//     * 启动
//     */
//    public void start() {
//        if (mSocketConfig.isPrintLog()) {
//            Logger.d(TAG, mSocketConfig.getName() + " --start connect");
//        }
//        if (mSocketThread != null) {
//            mSocketThread.quit();
//        }
//        mSocketThread = new SocketThread();
//        mSocketThread.start();
//    }
//
//    /**
//     * 断开连接
//     */
//    public void disconnect() {
//        if (mReadThread != null) {
//            mReadThread.quit();
//            mReadThread = null;
//        }
//        Socket socket = mSocket;
//        if (socket != null) {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        setConnectState(ConnectState.DISCONNECT);
//    }
//
//    /**
//     * 退出
//     */
//    public void quit() {
//        if (mSocketConfig.isPrintLog()) {
//            Logger.d(TAG, mSocketConfig.getName() + " --quit");
//        }
//        if (mSocketThread != null) {
//            mSocketThread.quit();
//            mSocketThread = null;
//        }
//    }
//
//    /**
//     * socket线程
//     */
//    private class SocketThread extends Thread {
//
//        private AtomicBoolean isRun = new AtomicBoolean(true);
//
//        @Override
//        public void run() {
//            try {
//                mServerSocket = new ServerSocket(mSocketConfig.getPort());
//                if (mSocketConfig.isReuseAddress()) {
//                    mServerSocket.setReuseAddress(true);
//                }
//                setConnectState(ConnectState.SERVER_START);
//                while (isRun.get()) {
//                    Socket socket = mServerSocket.accept();
//                    handleAccept(socket);
//                }
//            } catch (Exception e) {
//                setConnectState(ConnectState.ERR_LOCAL);
//                e.printStackTrace();
//            } finally {
//                isRun.set(false);
//                closeSocket();
//            }
//        }
//
//
//        public void quit() {
//            if (isRun.compareAndSet(true, false)) {
//                closeSocket();
//                try {
//                    join(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                setConnectState(ConnectState.SERVER_QUIT);
//            }
//        }
//
//        private void closeSocket() {
//            if (mReadThread != null) {
//                mReadThread.quit();
//                mReadThread = null;
//            }
//            if (mSocket != null) {
//                try {
//                    mSocket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                mSocket = null;
//            }
//            if (mServerSocket != null) {
//                try {
//                    mServerSocket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                mServerSocket = null;
//            }
//        }
//    }
//
//
//    private void handleAccept(Socket socket) {
//        Log.i(TAG, "handleAccept: socket address is " + socket.getRemoteSocketAddress());
//        if (mReadThread != null) {
//            mReadThread.quit();
//        }
//        Socket curSocket = mSocket;
//        Log.d(TAG, "handleAccept: current socket is " + curSocket);
//        if (curSocket != null && curSocket.isConnected()) {
//            //关闭之前的连接
//            try {
//                Log.i(TAG, "handleAccept: close current socket: " + curSocket.getRemoteSocketAddress());
//                curSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            setConnectState(ConnectState.DISCONNECT);
//        }
//        mSocket = socket;
//        setConnectState(ConnectState.CONNECTED);
//        mReadThread = new ReadThread(socket);
//        mReadThread.start();
//    }
//
//    /**
//     * 处理socket通道异常
//     *
//     * @param socket 发生异常的socket
//     * @param e      异常
//     */
//    public synchronized void handleSocketChannelException(Socket socket, Exception e) {
//        Logger.i(TAG, "handleSocketChannelException: socket close socket address is " + socket.getRemoteSocketAddress());
//        e.printStackTrace();
//        try {
//            socket.close();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        if (mSocket != null) {
//            Logger.d(TAG, "handleSocketChannelException: current socket address is " + mSocket.getRemoteSocketAddress());
//        }
//        if (mSocket != null && mSocket.equals(socket)) {
//            setConnectState(ConnectState.DISCONNECT);
//        }
//    }
//
//
//    /**
//     * 发送数据到主线程
//     *
//     * @param data 数据
//     */
//    private void postDataPacket(byte[] data, int dataType, int dataPacketType) {
//        DataPacket dataPacket = new DataPacket();
//        InetSocketAddress remoteAddress = getRemoteAddress();
//        if (remoteAddress != null) {
//            dataPacket.setAddressIp(remoteAddress.getHostString());
//            dataPacket.setAddressPort(remoteAddress.getPort());
//            if (mSocketConfig.isPrintLog()) {
//                Logger.d(TAG, mSocketConfig.getName() + "postDataPacket: dataPacket ip = " + dataPacket.getAddressIp() + ",port = " + dataPacket.getAddressPort());
//            }
//        }
//        dataPacket.setData(data);
//        dataPacket.setDataType(dataType);
//        dataPacket.setDataPacketType(dataPacketType);
//        notifyDataPacketReceived(dataPacket);
//        if (mSocketConfig.isPrintLog()) {
//            Logger.d(TAG, String.format("postDataPacket: Post Received Data,dataPacketType = %s,dataType = %s", dataPacketType, dataType));
//        }
//    }
//
//    @Nullable
//    public InetSocketAddress getRemoteAddress() {
//        if (mSocket == null) {
//            return null;
//        }
//        SocketAddress socketAddress = mSocket.getRemoteSocketAddress();
//        if (socketAddress instanceof InetSocketAddress) {
//            return ((InetSocketAddress) socketAddress);
//        } else {
//            Logger.e(TAG, "socketAddress is not instanceof InetSocketAddress");
//            return null;
//        }
//    }
//
//    /**
//     * 通知接收到数据包
//     *
//     * @param dataPacket 数据包
//     */
//    private void notifyDataPacketReceived(final DataPacket dataPacket) {
//        ThreadManager.getInstance().runOnMainThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mTcpClientCallback != null) {
//                    mTcpClientCallback.onDataPacketReceived(dataPacket);
//                }
//            }
//        });
//    }
//
//    /**
//     * 发送数据
//     */
//    @WorkerThread
//    public synchronized void send(String msg) {
//        if (msg == null) {
//            return;
//        }
//        byte[] bytes = msg.getBytes();
//        send(bytes);
//    }
//
//    /**
//     * 发送数据
//     */
//    @WorkerThread
//    public synchronized void send(ByteBuffer buffer) {
//        if (buffer == null) {
//            return;
//        }
//        if (mSocketConfig.isPrintLog()) {
//            String address = mSocket.getRemoteSocketAddress().toString();
//            Logger.d(TAG, mSocketConfig.getName() + "send: ready send byte data to " + address);
//        }
//        Logger.d(TAG, String.format("send: position = %s,remaining = %s", buffer.position(), buffer.remaining()));
//        Logger.d(TAG, String.format("send: buffer = %s", DataPackageUtil.bytesToHexString(buffer.array())));
//        try {
//            DataOutputStream dos = new DataOutputStream(mSocket.getOutputStream());
//            dos.write(buffer.array(), buffer.position(), buffer.remaining());
//            dos.flush();
//        } catch (IOException e) {
//            handleSocketChannelException(mSocket, e);
//        }
//        if (mTcpClientCallback != null) {
//            mTcpClientCallback.onBufferDataSendDone(buffer);
//        }
//        if (mSocketConfig.isPrintLog()) {
//            String address = mSocket.getRemoteSocketAddress().toString();
//            Logger.d(TAG, mSocketConfig.getName() + "send: finish send byte data to " + address);
//        }
//    }
//
//    /**
//     * 发送数据
//     */
//    @WorkerThread
//    public synchronized void send(byte[] bytes) {
//        if (bytes == null || mSocket == null) {
//            return;
//        }
//        if (mSocketConfig.isPrintLog()) {
//            String address = mSocket.getRemoteSocketAddress().toString();
//            Logger.d(TAG, mSocketConfig.getName() + "send: ready send byte data to " + address);
//        }
//        try {
//            DataOutputStream dos = new DataOutputStream(mSocket.getOutputStream());
//            dos.write(bytes);
//            dos.flush();
//        } catch (IOException e) {
//            handleSocketChannelException(mSocket, e);
//        }
//        if (mSocketConfig.isPrintLog()) {
//            String address = mSocket.getRemoteSocketAddress().toString();
//            Logger.d(TAG, mSocketConfig.getName() + "send: finish send byte data to " + address);
//        }
//    }
//
//    /**
//     * 重置参数
//     */
//    private void reset() {
//        mSocket = null;
//        mReceiveBuffer.clear();
//        mWriteBufferQueue.clear();
//        mDataParser.reset();
//    }
//
//    /**
//     * 设置连接状态
//     *
//     * @param connectState 连接状态
//     */
//    private synchronized void setConnectState(ConnectState connectState) {
//        if (mSocketConfig.isPrintLog()) {
//            Logger.d(TAG, mSocketConfig.getName() + "setConnectState connectState = " + connectState);
//        }
//        if (mConnectState != connectState) {
//            mConnectState = connectState;
//            switch (connectState) {
//                case DISCONNECT:
//                    reset();
//                    break;
//                case SERVER_QUIT:
//                    reset();
//                    break;
//            }
//            notifyConnectStateChanged(connectState);
//        }
//    }
//
//    /**
//     * 通知连接状态变化
//     *
//     * @param connectState 连接状态
//     */
//    private void notifyConnectStateChanged(final ConnectState connectState) {
//        ThreadManager.getInstance().runOnMainThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mTcpClientCallback != null) {
//                    mTcpClientCallback.onConnectStateChanged(connectState);
//                }
//            }
//        });
//    }
//
//    /**
//     * 获取连接状态
//     *
//     * @return 连接状态
//     */
//    public ConnectState getConnectState() {
//        return mConnectState;
//    }
//
//    /**
//     * 是否已连接
//     *
//     * @return true为已连接
//     */
//    public boolean isConnected() {
//        return mConnectState == ConnectState.CONNECTED;
//    }
//
//
//    private class ReadThread extends Thread {
//        private Socket mSocket;
//        private volatile boolean isRun = true;
//
//        public ReadThread(Socket socket) {
//            mSocket = socket;
//        }
//
//        @Override
//        public void run() {
//            DataInputStream dataInputStream = null;
//            try {
//                while (isRun) {
//                    if (dataInputStream == null) {
//                        dataInputStream = new DataInputStream(mSocket.getInputStream());
//                    }
//                    byte[] buffer = new byte[mSocketConfig.getPieceSize()];
//                    int readCount = dataInputStream.read(buffer);
//                    if (readCount == -1) {
//                        Logger.e(TAG, "receiveFrom: read error,socket address is " + mSocket.getRemoteSocketAddress());
//                        throw new TcpException("socket unreadable", TcpException.ERR_SOCKET_UNREADABLE);
//                    } else if (readCount == 0) {
//                        return;
//                    }
//                    byte[] currentPiece = new byte[readCount];
//                    System.arraycopy(buffer, 0, currentPiece, 0, currentPiece.length);
//                    Logger.i(TAG, "readDataPacket: currentPiece = " + DataPackageUtil.bytesToHexString(currentPiece));
//                    // 是否需要解包
//                    if (mSocketConfig.isNeedUnpack()) {
//                        mDataParser.unpack(currentPiece, new DataParser.Callback() {
//                            @Override
//                            public void onDataUnpacked(byte[] data, int dataType, int dataPacketType) {
//                                postDataPacket(data, dataType, dataPacketType);
//                            }
//                        });
//                    } else {
//                        // 不需要解包，每次按照1024个字节读取缓存池数据，并直接抛出给外部
//                        // 此方式太过简单粗暴，不关心数据内容，只要有收到数据即可
//                        postDataPacket(buffer, MTDataPacketType.MEITU_MSG, 0);
//                    }
//                }
//            } catch (IOException e) {
//                handleSocketChannelException(mSocket, e);
//                try {
//                    join();
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//
//        public void quit() {
//            isRun = false;
//            try {
//                join(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
