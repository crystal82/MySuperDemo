package com.knight.jone.mySuperDemo.socket;


/**
 * socket配置
 */
public class SocketConfig {

    // 名字
    private String mName;
    // 是否打印日志
    private boolean mPrintLog = true;
    //启用的端口
    private int mPort;
    private boolean mReuseAddress;
    // 是否需要解包
    private boolean mIsNeedUnpack;
    // 分片大小
    private int mPieceSize = 1024;
    //写缓冲大小
    private int mSendBufferSize = 0;
    //写数据阻塞时间
    private long mWriteBlockTime = 0;

    public String getName() {
        return mName;
    }

    public boolean isPrintLog() {
        return mPrintLog;
    }

    public int getPort() {
        return mPort;
    }

    public boolean isReuseAddress() {
        return mReuseAddress;
    }

    public boolean isNeedUnpack() {
        return mIsNeedUnpack;
    }

    public int getPieceSize() {
        return mPieceSize;
    }

    public long getWriteBlockTime() {
        return mWriteBlockTime;
    }

    public int getSendBufferSize() {
        return mSendBufferSize;
    }

    public static class Builder {
        private String mName;
        // 是否打印日志
        private boolean mPrintLog = true;
        private int mPort;
        private boolean mReuseAddress = true;
        // 默认不需要解包
        private boolean mIsNeedUnpack = false;
        // 分片大小
        private int mPieceSize = 1024;
        //写缓冲大小
        private int mSendBufferSize = 0;
        //写数据阻塞时间
        private long mWriteBlockTime = 0;

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setPrintLog(boolean printLog) {
            mPrintLog = printLog;
            return this;
        }

        public Builder setPort(int port) {
            mPort = port;
            return this;
        }

        /**
         * @see java.net.Socket#setReuseAddress(boolean)
         */
        public Builder setReuseAddress(boolean reuseAddress) {
            mReuseAddress = reuseAddress;
            return this;
        }

        /**
         * 是否需要解包，默认不需要 每次按照byte[1024]读取缓存数据
         *
         * @param isNeedUnpack 是否需要解包
         */
        public Builder setIsNeedUnpack(boolean isNeedUnpack) {
            mIsNeedUnpack = isNeedUnpack;
            return this;
        }

        /**
         * 设置数据包分片大小
         *
         * @param pieceSize 分片大小
         */
        public Builder setPieceSize(int pieceSize) {
            mPieceSize = pieceSize;
            return this;
        }

        /**
         * 设置写缓冲大小
         *
         * @param sendBufferSize 写缓冲大小
         */
        public Builder setSendBufferSize(int sendBufferSize) {
            this.mSendBufferSize = sendBufferSize;
            return this;
        }

        /**
         * 设置写数据阻塞时间
         *
         * @param writeBlockTime 写数据阻塞时间
         */
        public Builder setWriteBlockTime(long writeBlockTime) {
            this.mWriteBlockTime = writeBlockTime;
            return this;
        }

        public SocketConfig create() {
            SocketConfig config = new SocketConfig();
            config.mName = mName;
            config.mPrintLog = mPrintLog;
            config.mPort = mPort;
            config.mReuseAddress = mReuseAddress;
            config.mIsNeedUnpack = mIsNeedUnpack;
            config.mPieceSize = mPieceSize;
            config.mSendBufferSize = mSendBufferSize;
            config.mWriteBlockTime = mWriteBlockTime;
            return config;
        }
    }
}

