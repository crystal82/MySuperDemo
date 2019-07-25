package com.knight.jone.mySuperDemo.socket;


/**
 * udp客户端回调
 */
public interface IUdpClientCallback {

    /**
     * 接收到数据包回调
     *
     * @param dataPacket 数据包
     */
    void onDataPacketReceived(DataPacket dataPacket);

}
