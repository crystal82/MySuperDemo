package com.knight.jone.mySuperDemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.knight.jone.mySuperDemo.MyApplication;

import java.util.Map;

/**
 * SharedPreferences工具类
 * 这个相当好用 多用多用
 */
public class SpHelper {

    private static SharedPreferences        sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static final String FILE_NAME = "shared_data";//保存文件名

    private SpHelper() {
    }
    public static SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }
    public static void initSP(Context context) {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void putApply(String key, Object object) {
        if(editor == null){
            initSP(MyApplication.getInstance());
        }
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    public static boolean putCommit(String key, Object object) {
        if(editor == null){
            initSP(MyApplication.getInstance());
        }
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        return editor.commit();
    }

    /**
     * 获取保存数据的方法，我们根据默认值的到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key           键的值
     * @param defaultObject 默认值
     * @return
     */

    public static Object get(String key, Object defaultObject) {
        if(sharedPreferences == null){
            initSP(MyApplication.getInstance());
        }
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key,
                                              (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, null);
        }
    }


    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有的数据
     */
    public static void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }
}
