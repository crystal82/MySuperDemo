package com.knight.jone.mySuperDemo.ndk;

/**
 * 尝试NDK开发
 *
 * 	javac JNIDemo.java -h .  （注意加 "."）
 */
public class NDKTools {

    static {
        System.loadLibrary("native-lib");
    }
    public static native String getStringFromNDK();
}
