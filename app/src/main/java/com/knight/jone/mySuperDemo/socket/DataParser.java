package com.knight.jone.mySuperDemo.socket;


import androidx.annotation.NonNull;

/**
 * 数据包解析器
 */
public class DataParser {

    private static final String TAG = "DataParser";

    // 之前分片
    private byte[] beforePiece = null;
    private int startIndex = -1;

    public interface Callback {
        /**
         * 数据解包回调
         */
        void onDataUnpacked(byte[] data, int dataType, int dataPacketType);
    }

    /**
     * 数据解包
     *
     * @param currentPiece 当前分片数据
     */
    public void unpack(@NonNull byte[] currentPiece, Callback callback) {

    }

    /**
     * 重置
     */
    public void reset() {
        beforePiece = null;
        startIndex = -1;
    }

}
