package com.arialyy.frame.util;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author lyy
 */
public class StringUtil {
  public static final int APPLICATION = 0;
  public static final int BROADCAST = 1;
  public static final int SERVICE = 2;
  public static final int ACTIVITY = 3;

  /**
   * 获取字体长度
   */
  public static int getTextLen(TextView textView) {
    TextPaint paint = textView.getPaint();
    return (int) Layout.getDesiredWidth(textView.getText().toString(), 0,
        textView.getText().length(), paint);
  }

  /**
   * 给某段支付设置下划线
   */
  public static SpannableString underLineHight(String str, String underLineStr) {
    if (!str.contains(underLineStr)) {
      return null;
    }
    // 创建一个 SpannableString对象
    SpannableString sp = new SpannableString(str);
    int index = str.indexOf(underLineStr);
    //设置背景颜色, StrikethroughSpan()是设置中划线
    sp.setSpan(new UnderlineSpan(), index, index + underLineStr.length(),
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    return sp;
  }

  /**
   * 高亮所有关键字
   *
   * @param str 这个字符串
   * @param key 关键字
   */
  public static SpannableString highlightKeyword(String str, String key, int highlightColor) {
    if (!str.contains(key)) {
      return null;
    }
    SpannableString sp = new SpannableString(str);
    key = Pattern.quote(key);
    Pattern p = Pattern.compile(key);
    Matcher m = p.matcher(str);

    while (m.find()) {  //通过正则查找，逐个高亮
      int start = m.start();
      int end = m.end();
      sp.setSpan(new ForegroundColorSpan(highlightColor), start, end,
          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    return sp;
  }

  /**
   * 创建一个含有超链接的字符串
   *
   * @param text 整段字符串
   * @param clickText 含有超链接的字符
   * @param url 超链接
   */
  public static SpannableString createLinkText(String text, String clickText, String url) {
    if (!text.contains(clickText)) {
      return null;
    }
    SpannableString sp = new SpannableString(text);
    int index = text.indexOf(clickText);
    // 设置超链接
    sp.setSpan(new URLSpan(url), index, index + clickText.length(),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return sp;
  }

  /**
   * 将缓存的key转换为hash码
   *
   * @param key 缓存的key
   * @return 转换后的key的值, 系统便是通过该key来读写缓存
   */
  public static String keyToHashKey(String key) {
    String cacheKey;
    try {
      final MessageDigest mDigest = MessageDigest.getInstance("MD5");
      mDigest.update(key.getBytes());
      cacheKey = bytesToHexString(mDigest.digest());
    } catch (NoSuchAlgorithmException e) {
      cacheKey = String.valueOf(key.hashCode());
    }
    return cacheKey;
  }

  /**
   * 读取Activity节点的meta-data
   */
  public static String getActivityMetaData(Activity activity, String key) {
    try {
      return activity.getPackageManager()
          .getActivityInfo(activity.getComponentName(),
              PackageManager.GET_META_DATA).metaData.getString(key);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 读取Service节点的meta-data
   *
   * @param serviceClazz 服务的class
   */
  public static String getServiceMetaData(Context context, Class<? extends Service> serviceClazz,
      String key) {
    try {
      return context.getPackageManager()
          .getServiceInfo(new ComponentName(context, serviceClazz),
              PackageManager.GET_META_DATA).metaData.getString(key);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 读取BroadCast节点meta-data数据
   *
   * @param receiverClazz 广播接收器的class
   */
  public static String getBroadCasetMetaData(Context context,
      Class<? extends BroadcastReceiver> receiverClazz, String key) {
    try {
      return context.getPackageManager()
          .getReceiverInfo(new ComponentName(context, receiverClazz),
              PackageManager.GET_META_DATA).metaData.getString(key);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 读取Application节点的meta-data数据
   */
  public static String getApplicationMetaData(Context context, String key) {
    try {
      return context.getPackageManager()
          .getApplicationInfo(context.getPackageName(),
              PackageManager.GET_META_DATA).metaData.getString(key);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 用AES算法解密加密的密码
   *
   * @param seed 密钥
   * @param password 加密的密码
   * @return 解密后的密码, 默认返回""
   */
  public static String decryptPassword(String seed, String password) {
    try {
      return AESEncryption.decryptString(seed, password);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * 从XML读取字符串
   *
   * @param id 字符串id
   */
  public static String getStringFromXML(Context context, int id) {
    return context.getResources().getString(id);
  }

  /**
   * 从xml读取字符串数组
   */
  public static String[] getStringArrayFromXML(Context context, int id) {
    return context.getResources().getStringArray(id);
  }

  /**
   * 将字符串数组转换为list
   */
  public static List<String> stringArray2List(String[] strArray) {
    List<String> list = new ArrayList<String>();
    Collections.addAll(list, strArray);
    return list;
  }

  /**
   * 高亮整段字符串
   */
  public static SpannableStringBuilder highLightStr(String str, int color) {
    SpannableStringBuilder style = new SpannableStringBuilder(str);
    style.setSpan(new ForegroundColorSpan(color), 0, str.length(),
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    return style;
  }

  /**
   * 高亮代码片段
   *
   * @param str 整段字符串
   * @param highLightStr 要高亮的代码段
   * @param color 高亮颜色
   */
  public static SpannableStringBuilder highLightStr(String str, String highLightStr, int color) {
    int start = str.indexOf(highLightStr);
    if (start == -1) {
      return null;
    }
    SpannableStringBuilder style = new SpannableStringBuilder(str);
    // new BackgroundColorSpan(Color.RED)背景高亮
    // ForegroundColorSpan(Color.RED) 字体高亮
    style.setSpan(new ForegroundColorSpan(color), start, start + highLightStr.length(),
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    return style;
  }

  /**
   * 字符串转hashcode
   */
  public static int keyToHashCode(String str) {
    int total = 0;
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      if (ch == '-') ch = (char) 28; // does not contain the same last 5 bits as any letter
      if (ch == '\'') ch = (char) 29; // nor this
      total = (total * 33) + (ch & 0x1F);
    }
    return total;
  }

  /**
   * 字符串转dbouble
   */
  public static double strToDouble(String str) {
    // double d = Double.parseDouble(str);

    /* 以下代码处理精度问题 */
    BigDecimal bDeci = new BigDecimal(str);
    // BigDecimal chushu =new BigDecimal(100000000);
    // BigDecimal result =bDeci.divide(chushu,new
    // MathContext(4));//MathConText(4)表示结果精确4位！
    // return result.doubleValue() * 100000000;
    return bDeci.doubleValue();
  }

  /**
   * 将普通字符串转换为16位进制字符串
   */
  public static String bytesToHexString(byte[] src) {
    StringBuilder stringBuilder = new StringBuilder("0x");
    if (src == null || src.length <= 0) {
      return null;
    }
    char[] buffer = new char[2];
    for (byte aSrc : src) {
      buffer[0] = Character.forDigit((aSrc >>> 4) & 0x0F, 16);
      buffer[1] = Character.forDigit(aSrc & 0x0F, 16);
      stringBuilder.append(buffer);
    }
    return stringBuilder.toString();
  }

  /**
   * 把字符串长度加满16位
   *
   * @return 16位长度的字符串
   */
  public static String addStrLenTo16(String str) {
    //由于汉字的特殊性，长度要用byte来判断
    for (int i = str.getBytes().length; i < 16; i++) {
      str += '\0';
    }
    return str;
  }

  /**
   * 获取对象名
   *
   * @param obj 对象
   * @return 对象名
   */
  public static String getClassName(Object obj) {
    String arrays[] = obj.getClass().getName().split("\\.");
    return arrays[arrays.length - 1];
  }
}
