package com.knight.jone.mySuperDemo.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * 作者：HWQ on 2017/6/5 11:53
 * 描述：
 */
public final class Cockroach {

    public interface ExceptionHandler {
        //异常处理接口
        void handlerException(Thread thread, Throwable throwable);
    }

    private Cockroach() {
    }

    private static ExceptionHandler                sExceptionHandler;
    private static Thread.UncaughtExceptionHandler sUncaughtExceptionHandler;//DefaultUncaughtExceptionHandler
    private static boolean sInstalled = false;//标记位，避免重复安装卸载

    /**
     * 当主线程或子线程抛出异常时会调用exceptionHandler.handlerException(Thread thread, Throwable throwable)
     * <p>
     * exceptionHandler.handlerException可能运行在非UI线程中。
     * <p>
     * 若设置了Thread.setDefaultUncaughtExceptionHandler则可能无法捕获子线程异常。
     *
     * @param exceptionHandler
     */
    public static synchronized void  install(ExceptionHandler exceptionHandler) {
        if (sInstalled) {
            return;
        }
        sInstalled = true;
        sExceptionHandler = exceptionHandler;

        //捕获主线出现的异常避免App崩溃
        //通过Handler.post方法启动Looper，避免主线程卡死
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        //Binder.clearCallingIdentity();
                        //处理卸载时抛出异常
                        if (e instanceof QuitCockroachException) {
                            return;
                        }

                        //捕获异常，执行自定义处理方法
                        if (sExceptionHandler != null) {
                            sExceptionHandler.handlerException(Looper.getMainLooper().getThread(), e);
                        }
                    }
                }
            }
        });

        //保存默认的异常处理机制，卸载Cockroach时使用
        sUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //注册全局异常处理方法
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (sExceptionHandler != null) {
                    sExceptionHandler.handlerException(t, e);
                }
            }
        });

    }

    public static synchronized void uninstall() {
        if (!sInstalled) {
            return;
        }
        sInstalled = false;
        sExceptionHandler = null;
        //卸载后恢复默认的异常处理逻辑，否则主线程再次抛出异常后将导致ANR，并且无法捕获到异常位置
        Thread.setDefaultUncaughtExceptionHandler(sUncaughtExceptionHandler);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                throw new QuitCockroachException("Quit Cockroach.....");//主线程抛出异常，迫使 while (true) {}结束
            }
        });

    }
}

final class QuitCockroachException extends RuntimeException {
    public QuitCockroachException(String message) {
        super(message);
    }
}