package com.knight.jone.mySuperDemo.socket;

import android.text.TextUtils;

import com.knight.jone.mySuperDemo.utils.Lg;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SocketUtil {

    public interface RequestCallback {
        void onSuccess(String backInfo);

        void onTimeOut(String ipString, int port);
    }

    /**
     * socket广播数据
     */
    public static void sendDatagramPacketBroadcast(String ipAddress, String sendData, int port, String replyData, RequestCallback callback) {
        Lg.d("sendDatagramPacketBroadcast ipAddress:" + ipAddress + "sendData:" + sendData + "  port:" + port + "   replyData:" + replyData);
        // 创建广播
        InetAddress broadcastAddress;
        try {
            //"255.255.255.255"
            broadcastAddress = InetAddress.getByName(ipAddress);

            DatagramSocket socket = new DatagramSocket();
            // 发送数据
            DatagramPacket sendPacket = new DatagramPacket(sendData.getBytes(), sendData.getBytes().length, broadcastAddress, port);
            socket.send(sendPacket);
            socket.send(sendPacket);
            socket.send(sendPacket);
            socket.send(sendPacket);
            socket.send(sendPacket);

            // 接收数据
            byte[] buffer = new byte[1024 * 1024];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

            //阻塞等待返回数据
            socket.receive(receivePacket);
            String message = new String(receivePacket.getData()).trim();
            //请求成功回调数据
            callback.onSuccess(message);

            // 回复数据
            if (!TextUtils.isEmpty(replyData)) {
                DatagramPacket reply = new DatagramPacket(replyData.getBytes(), replyData.getBytes().length, receivePacket.getAddress(), receivePacket.getPort());
                socket.send(reply);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}