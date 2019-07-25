package com.knight.jone.mySuperDemo.socket;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;


public class UdpClinetTest {
    private static final int MAXRECEIVED = 255;

    public static void main(String[] args) throws IOException {
        byte[] msg = new String("connect test successfully!!!").getBytes();

        DatagramSocket client = new DatagramSocket();

        InetAddress inetAddr = InetAddress.getLocalHost();
        SocketAddress socketAddr = new InetSocketAddress("192.168.43.223", 6060);

        DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, socketAddr);

        client.send(sendPacket);

        client.close();
    }

    public void broadCast() throws IOException {
        byte[] msg = new String("connection successfully!!!").getBytes();
        /*
         * 在Java UDP中单播与广播的代码是相同的,要实现具有广播功能的程序只需要使用广播地址即可, 例如：这里使用了本地的广播地址
         */
        InetAddress inetAddr = InetAddress.getByName("255.255.255.255");
        DatagramSocket client = new DatagramSocket();

        DatagramPacket sendPack = new DatagramPacket(msg, msg.length, inetAddr,
                8888);

        client.send(sendPack);
        System.out.println("Client send msg complete");
        client.close();
    }

    public void broadcastReceive() throws IOException {
        DatagramPacket receive = new DatagramPacket(new byte[1024], 1024);
        DatagramSocket server = new DatagramSocket(8888);

        System.out.println("---------------------------------");
        System.out.println("Server current start......");
        System.out.println("---------------------------------");

        while (true) {
            server.receive(receive);

            byte[] recvByte = Arrays.copyOfRange(receive.getData(), 0,
                    receive.getLength());

            System.out.println("Server receive msg:" + new String(recvByte));
        }
    }
}