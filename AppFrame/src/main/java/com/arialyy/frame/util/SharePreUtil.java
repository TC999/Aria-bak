package com.arialyy.frame.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * 配置文件工具类
 *
 * @author Administrator
 */
public class SharePreUtil {

  /**
   * 删除键值对
   */
  public static void removeKey(String preName, Context context, String key) {
    SharedPreferences pre = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
    Editor editor = pre.edit();
    editor.remove(key);
    editor.commit();
  }

  /**
   * 从配置文件读取字符串
   *
   * @param preName 配置文件名
   * @param key 字符串键值
   * @return 键值对应的字符串, 默认返回""
   */
  public static String getString(String preName, Context context, String key) {
    SharedPreferences pre = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
    return pre.getString(key, "");
  }

  /**
   * 从配置文件读取int数据
   *
   * @param preName 配置文件名
   * @param key int的键值
   * @return 键值对应的int, 默认返回-1
   */
  public static int getInt(String preName, Context context, String key) {
    SharedPreferences pre = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
    return pre.getInt(key, -1);
  }

  /**
   * 从配置文件读取Boolean值
   *
   * @return 如果没有，默认返回false
   */
  public static Boolean getBoolean(String preName, Context context, String key) {
    SharedPreferences pre = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
    return pre.getBoolean(key, false);
  }

  /**
   * 从配置文件获取float数据
   *
   * @return 默认返回0.0f
   */
  public static float getFloat(String preName, Context context, String key) {
    SharedPreferences pre = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
    return pre.getFloat(key, 0.0f);
  }

  /**
   * 从配置文件获取对象
   */
  public static <T> T getObject(String preName, Context context, String key, Class<T> clazz) {
    SharedPreferences pre = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
    String str = pre.getString(key, "");
    return TextUtils.isEmpty(str) ? null : new Gson().fromJson(str, clazz);
  }

  /**
   * 存储字符串到配置文件
   *
   * @param preName 配置文件名
   * @param key 存储的键值
   * @param value 需要存储的字符串
   * @return 成功标志
   */
  public static Boolean putString(String preName, Context context, String key, String value) {
    SharedPreferences pre = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
    Editor editor = pre.edit();
    editor.putString(key, value);
    return editor.commit();
  }

  /**
   * 保存Float数据到配置文件
   */
  public static Boolean putFloat(String preName, Context context, String key, float value) {
    SharedPreferences pre = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
    Editor editor = pre.edit();
    editor.putFloat(key, value);
    return editor.commit();
  }

  /**
   * 存储数字到配置文件
   *
   * @param preName 配置文件名
   * @param key 存储的键值
   * @param value 需要存储的数字
   * @return 成功标志
   */
  public static Boolean putInt(String preName, Context context, String key, int value) {
    SharedPreferences pre = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
    Editor editor = pre.edit();
    editor.putInt(key, value);
    return editor.commit();
  }

  /**
   * 存储Boolean值到配置文件
   *
   * @param preName 配置文件名
   * @param key 键值
   * @param value 需要存储的boolean值
   */
  public static Boolean putBoolean(String preName, Context context, String key, Boolean value) {
    SharedPreferences pre = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
    Editor editor = pre.edit();
    editor.putBoolean(key, value);
    return editor.commit();
  }

  /**
   * 存放对象
   */
  public static Boolean putObject(String preName, Context context, String key, Class<?> clazz,
      Object obj) {
    String str = new Gson().toJson(obj, clazz);
    SharedPreferences pre = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
    Editor editor = pre.edit();
    editor.putString(key, str);
    return editor.commit();
  }
}
