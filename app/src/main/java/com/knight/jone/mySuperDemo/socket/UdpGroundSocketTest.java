package com.knight.jone.mySuperDemo.socket;

import com.knight.jone.mySuperDemo.utils.Lg;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.atomic.AtomicBoolean;

public class UdpGroundSocketTest {

    public static void main(String[] args) {
        final MulticastSocket socket = createMulticastGroupAndJoin("224.0.0.0", 5000);  //加入组播组，设置组播组的监听端口为5000
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendData(socket, "luanpeng".getBytes(), "224.0.0.0");  //向组播组发送数据
            }
        }).start();
        String message = recieveData(socket, "224.0.0.0");//接收组播组传来的消息
        System.out.println(message);
    }

    public static MulticastSocket createMulticastGroupAndJoin(String groupurl, int port) // 创建一个组播组并加入此组的函数
    {
        try {
            InetAddress group = InetAddress.getByName(groupurl); // 设置组播组的地址为239.0.0.0
            MulticastSocket socket = new MulticastSocket(port); // 初始化MulticastSocket类并将端口号与之关联
            socket.setTimeToLive(1); // 设置组播数据报的发送范围为本地网络
            socket.setSoTimeout(10000); // 设置套接字的接收数据报的最长时间
            socket.joinGroup(group); // 加入此组播组
            return socket;
        } catch (Exception e1) {
            System.out.println("Error: " + e1); // 捕捉异常情况
            return null;
        }
    }

    // 向组播组发送数据的函数
    public static void sendData(MulticastSocket socket, byte[] data, String groupurl) {
        try {
            InetAddress group = InetAddress.getByName(groupurl);
            // 存储在数组中
            DatagramPacket packet = new DatagramPacket(data, data.length, group, socket.getPort()); // 初始化DatagramPacket
            socket.send(packet); // 通过MulticastSocket实例端口向组播组发送数据
            System.out.println("以UDP形式发送组播报文");
        } catch (Exception e1) {
            System.out.println("Error: " + e1); // 捕捉异常情况
        }
    }

    public static String recieveData(MulticastSocket socket, String groupurl) {
        String message;
        try {
            InetAddress group = InetAddress.getByName(groupurl);
            byte[] data = new byte[512];
            DatagramPacket packet = new DatagramPacket(data, data.length, group, socket.getPort());
            // 通过MulticastSocket实例端口从组播组接收数据
            socket.receive(packet);
            // 将接受的数据转换成字符串形式
            message = new String(packet.getData());
        } catch (Exception e1) {
            System.out.println("Error: " + e1);
            message = "Error: " + e1;
        }
        return message;
    }
}
