package com.knight.jone.mySuperDemo.socket;

import androidx.annotation.WorkerThread;

import com.knight.jone.mySuperDemo.utils.Lg;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * udp连接客户端
 *
 * @author hxx1@meitu.com on 2019/5/10
 */
public class UdpSocketClient {

    private static final String TAG = "UdpSocketClient";
    private SocketConfig mSocketConfig;
    private IUdpClientCallback mUdpClientCallback;
    private SocketThread mSocketThread = null;
    private ConnectState mConnectState = ConnectState.NONE;
    private DataParser mDataPaser;
    private ByteBuffer mReceiveBuffer;
    private InetSocketAddress mTempInetSocketAddress = null;
    private DatagramSocket mDs;

    public UdpSocketClient(SocketConfig socketConfig) {
        this.mSocketConfig = socketConfig;
        this.mReceiveBuffer = ByteBuffer.allocate(mSocketConfig.getPieceSize());
        this.mDataPaser = new DataParser();
    }

    /**
     * 设置udp客户端回调
     *
     * @param udpClientCallback udp客户端回调
     */
    public void setUdpClientCallback(IUdpClientCallback udpClientCallback) {
        this.mUdpClientCallback = udpClientCallback;
    }

    /**
     * 启动
     */
    public void start() {
        if (mSocketConfig.isPrintLog()) {
            Lg.d(TAG, "Start UdpSocketClient:" + mSocketConfig.getPort() + "  name:" + mSocketConfig.getName() + " --start connect");
        }
        if (mSocketThread != null) {
            mSocketThread.quit();
        }
        mSocketThread = new SocketThread();
        mSocketThread.start();
    }

    /**
     * 退出
     */
    public void quit() {
        if (mSocketConfig.isPrintLog()) {
            Lg.d(TAG, mSocketConfig.getName() + " --quit");
        }
        if (mSocketThread != null) {
            mSocketThread.quit();
            mSocketThread = null;
        }
    }

    /**
     * socket线程
     */
    private class SocketThread extends Thread {

        private AtomicBoolean isRun = new AtomicBoolean(true);

        @Override
        public void run() {
            try {
                setConnectState(ConnectState.SERVER_START);
                DatagramSocket ds = new DatagramSocket(mSocketConfig.getPort());
                if (mSocketConfig.isReuseAddress()) {
                    ds.setReuseAddress(true);
                }
                if (mSocketConfig.getSendBufferSize() > 0) {
                    ds.setSendBufferSize(mSocketConfig.getSendBufferSize());
                }
                ds.setBroadcast(true);
                mDs = ds;
                Lg.d(TAG, "SocketThread DatagramSocket:" + mSocketConfig.getPort());
                while (isRun.get()) {
                    handleRead();
                }
            } catch (IOException e) {
                setConnectState(ConnectState.ERR_LOCAL);
                e.printStackTrace();
            } finally {
                isRun.set(false);
                closeSocket();
            }
        }

        public void quit() {
            Lg.d(TAG, "SocketThread quit");
            if (isRun.compareAndSet(true, false)) {
                closeSocket();
                try {
                    join(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reset();
            }
        }

        private void closeSocket() {
            Lg.d(TAG, "closeSocket");
            if (mDs != null) {
                mDs.close();
                mDs = null;
            }
        }
    }

    /**
     * 处理读操作
     */
    private void handleRead() {
        try {
            readDataPacket();
        } catch (IOException e) {
            handleSocketChannelException(e);
        }
    }

    /**
     * 读取并解析数据包
     */
    private void readDataPacket() throws IOException {
        byte[] buf = new byte[mSocketConfig.getPieceSize()];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        mDs.receive(dp);
        final SocketAddress socketAddress = dp.getSocketAddress();
        if (dp.getLength() > 0) {
            byte[] currentPiece;
            if (buf.length == dp.getLength()) {
                currentPiece = buf;
            } else {
                currentPiece = new byte[dp.getLength()];
                System.arraycopy(buf, 0, currentPiece, 0, dp.getLength());
            }
            // 是否需要解包
            if (mSocketConfig.isNeedUnpack()) {
                mDataPaser.unpack(currentPiece, new DataParser.Callback() {
                    @Override
                    public void onDataUnpacked(byte[] data, int dataType, int dataPacketType) {
                        postDataPacket(data, dataType, dataPacketType, socketAddress);
                    }
                });
            } else {
                // 不需要解包，每次按照1024个字节读取缓存池数据，并直接抛出给外部
                // 此方式太过简单粗暴，不关心数据内容，只要有收到数据即可
                postDataPacket(currentPiece, 11, 0, socketAddress);
            }
        }
    }


    /**
     * 处理socket通道异常
     * <p>
     * //     * @param datagramChannel 发生异常的socket通道
     *
     * @param e 异常
     */
    public synchronized void handleSocketChannelException(/*DatagramChannel datagramChannel,*/ Exception e) {
        Lg.e(TAG, "handleSocketChannelException: socket close socket address is null " + e/*+ datagramChannel.socket().getRemoteSocketAddress()*/);
        e.printStackTrace();
    }

    /**
     * 发送数据到主线程
     *
     * @param data 数据
     */
    private void postDataPacket(byte[] data, int dataType, int dataPacketType, SocketAddress socketAddress) {
        DataPacket dataPacket = new DataPacket();
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
            dataPacket.setAddressIp(inetSocketAddress.getHostString());
            dataPacket.setAddressPort(inetSocketAddress.getPort());
            if (mSocketConfig.isPrintLog()) {
                Lg.d(TAG, mSocketConfig.getName() + "postDataPacket: dataPacket ip = " + dataPacket.getAddressIp() + ",port = " + dataPacket.getAddressPort());
            }
        }
        dataPacket.setData(data);
        dataPacket.setDataType(dataType);
        dataPacket.setDataPacketType(dataPacketType);
        notifyDataPacketReceived(dataPacket);
        if (mSocketConfig.isPrintLog()) {
            Lg.d(TAG, String.format("postDataPacket: Post Received Data,dataPacketType = %s,dataType = %s", dataPacketType, dataType));
        }
    }

    /**
     * 通知接收到数据包
     *
     * @param dataPacket 数据包
     */
    private void notifyDataPacketReceived(final DataPacket dataPacket) {
        if (mUdpClientCallback != null) {
            mUdpClientCallback.onDataPacketReceived(dataPacket);
        }
    }

    /**
     * 发送数据
     */
    @WorkerThread
    public synchronized void send(String msg, String ip, int port) {
        if (mSocketConfig.isPrintLog()) {
            Lg.d(TAG, mSocketConfig.getName() + " --ready send data: " + msg + " to " + ip);
        }
        if (msg == null) {
            return;
        }
        byte[] bytes = msg.getBytes();
        send(bytes, ip, port);
    }

    /**
     * 发送数据
     */
    @WorkerThread
    public synchronized void send(byte[] bytes, String ip, int port) {
        if (bytes == null || ip == null) {
            return;
        }
        if (mSocketConfig.isPrintLog()) {
            Lg.i(TAG, mSocketConfig.getName() + " --ready send data: " + " to " + ip + ":" + port + ",data length = " + bytes.length);
        }

        if (mDs != null) {
            if (mTempInetSocketAddress == null || !mTempInetSocketAddress.getHostString().equals(ip) || mTempInetSocketAddress.getPort() != port) {
                mTempInetSocketAddress = new InetSocketAddress(ip, port);
            }

            Lg.d(TAG, "send is prepare:" + mTempInetSocketAddress.getHostString() + "   " + mTempInetSocketAddress.getPort());
            try {
                DatagramPacket dp = new DatagramPacket(bytes, bytes.length, mTempInetSocketAddress);
                mDs.send(dp);
                if (mSocketConfig.getWriteBlockTime() > 0) {
                    try {
                        Thread.sleep(mSocketConfig.getWriteBlockTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                Lg.d(TAG, "udp send:" + e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 重置参数
     */
    private void reset() {
        mReceiveBuffer.clear();
        mDataPaser.reset();
    }

    /**
     * 设置连接状态
     *
     * @param connectState 连接状态
     */
    private synchronized void setConnectState(ConnectState connectState) {
        if (mSocketConfig.isPrintLog()) {
            Lg.d(TAG, mSocketConfig.getName() + "setConnectState connectState = " + connectState);
        }
        if (mConnectState != connectState) {
            mConnectState = connectState;
        }
    }

}
