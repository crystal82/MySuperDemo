package com.knight.jone.mySuperDemo.utils

import android.os.Trace

var isSystraceEnabled: Boolean = false

/**
 * 通过 [Trace] 跟踪指定的代码块。
 *
 * @param sessionName 跟踪代码块的名字
 * @param block 跟踪的代码块
 * @return 带有返回值
 */
inline fun <T> systrace(sessionName: String, crossinline block: () -> T): T {
    val result: T
    try {
        if (isSystraceEnabled) {
            Trace.beginSection(sessionName)
        }
        result = block()
    } finally {
        if (isSystraceEnabled) {
            Trace.endSection()
        }
    }
    return result
}
