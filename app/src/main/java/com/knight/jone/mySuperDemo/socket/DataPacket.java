package com.knight.jone.mySuperDemo.socket;

/**
 * 接受到的数据包信息
 */

public class DataPacket {

    // 数据包
    private byte[] mData;
    // 数据类型
    private int mDataType;
    // 数据包类型
    private int mDataPacketType;
    // 来源ip
    private String mAddressIp;
    // 来源端口
    private int mAddressPort;


    public byte[] getData() {
        return mData;
    }

    public void setData(byte[] data) {
        this.mData = data;
    }

    public int getDataPacketType() {
        return mDataPacketType;
    }

    public void setDataPacketType(int mDataPacketType) {
        this.mDataPacketType = mDataPacketType;
    }

    public String getAddressIp() {
        return mAddressIp;
    }

    public void setAddressIp(String mAddressIp) {
        this.mAddressIp = mAddressIp;
    }

    public int getAddressPort() {
        return mAddressPort;
    }

    public void setAddressPort(int addressPort) {
        this.mAddressPort = addressPort;
    }

    public int getDataType() {
        return mDataType;
    }

    public void setDataType(int mDataType) {
        this.mDataType = mDataType;
    }
}
