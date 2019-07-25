package com.knight.jone.mySuperDemo.io;

import androidx.core.util.Pools;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class TutorialBuffer {

    private Pools.SimplePool<ByteBuffer> byteBufferSimplePool;

    public void initBufferPool(int size) {
        byteBufferSimplePool = new Pools.SimplePool<>(size);
    }

    public void initBuffer() {
        ByteBuffer allocate = ByteBuffer.allocate(5);
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{});
    }

    public String decodeKey(ByteBuffer bytes) {
        Charset charset = Charset.forName("utf-8");
        return charset.decode(bytes).toString();
    }

    public byte[] decodeValue(ByteBuffer bytes) {
        int len = bytes.limit() - bytes.position();
        byte[] bytes1 = new byte[len];
        bytes.get(bytes1);
        return bytes1;
    }

    public ByteBuffer encodeKey(String key) {
        try {
            return ByteBuffer.wrap(key.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ByteBuffer.wrap(key.getBytes());
    }

    public ByteBuffer encodeValue(byte[] value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(value.length);
        byteBuffer.clear();
        byteBuffer.get(value, 0, value.length);
        return byteBuffer;
    }
}
