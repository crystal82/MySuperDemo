package com.knight.jone.mySuperDemo.socket;

/**
 * 连接状态
 */
public enum ConnectState {
    /**
     * 初始状态
     */
    NONE,
    /**
     * 服务端启动
     */
    SERVER_START,
    /**
     * 已连接
     */
    CONNECTED,
    /**
     * 断开连接（服务端socket仍开启着，等待重连）
     */
    DISCONNECT,
    /**
     * 本地出错，例如热点关闭导致无法建立连接
     */
    ERR_LOCAL,
    /**
     * 服务端socket关闭
     */
    SERVER_QUIT,
}
