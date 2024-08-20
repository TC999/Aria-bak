package com.arialyy.frame.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 跟网络相关的工具类
 */
public class NetUtils {

  /**
   * 没有网络
   */
  public static final int NETWORK_TYPE_INVALID = 0;
  /**
   * wap网络
   */
  public static final int NETWORK_TYPE_WAP = 1;
  /**
   * 2G网络
   */
  public static final int NETWORK_TYPE_2G = 2;
  /**
   * 3G和3G以上网络，或统称为快速网络
   */
  public static final int NETWORK_TYPE_3G = 3;
  /**
   * wifi网络
   */
  public static final int NETWORK_TYPE_WIFI = 4;

  private NetUtils() {
    /* cannot be instantiated */
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  /**
   * 判断网络是否连接
   */
  public static boolean isConnected(Context context) {
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo ni = cm.getActiveNetworkInfo();
    return ni != null && ni.isConnectedOrConnecting();
  }

  /**
   * 判断是否是wifi连接
   */
  public static boolean isWifi(Context context) {
    return getNetWorkType(context) == NETWORK_TYPE_WIFI;
  }

  /**
   * 获取网络状态，wifi,wap,2g,3g.
   *
   * @param context 上下文
   * @return int 网络状态 {@link #NETWORK_TYPE_2G},{@link #NETWORK_TYPE_3G},
   * {@link #NETWORK_TYPE_INVALID},{@link #NETWORK_TYPE_WAP},{@link #NETWORK_TYPE_WIFI}
   */
  public static int getNetWorkType(Context context) {
    int netWorkType = -1;
    ConnectivityManager manager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      String type = networkInfo.getTypeName();
      if (type.equalsIgnoreCase("WIFI")) {
        netWorkType = NETWORK_TYPE_WIFI;
      } else if (type.equalsIgnoreCase("MOBILE")) {
        String proxyHost = android.net.Proxy.getDefaultHost();
        netWorkType = TextUtils.isEmpty(proxyHost)
            ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G)
            : NETWORK_TYPE_WAP;
      }
    } else {
      netWorkType = NETWORK_TYPE_INVALID;
    }
    return netWorkType;
  }

  private static boolean isFastMobileNetwork(Context context) {
    TelephonyManager telephonyManager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    switch (telephonyManager.getNetworkType()) {
      case TelephonyManager.NETWORK_TYPE_1xRTT:
        return false; // ~ 50-100 kbps
      case TelephonyManager.NETWORK_TYPE_CDMA:
        return false; // ~ 14-64 kbps
      case TelephonyManager.NETWORK_TYPE_EDGE:
        return false; // ~ 50-100 kbps
      case TelephonyManager.NETWORK_TYPE_EVDO_0:
        return true; // ~ 400-1000 kbps
      case TelephonyManager.NETWORK_TYPE_EVDO_A:
        return true; // ~ 600-1400 kbps
      case TelephonyManager.NETWORK_TYPE_GPRS:
        return false; // ~ 100 kbps
      case TelephonyManager.NETWORK_TYPE_HSDPA:
        return true; // ~ 2-14 Mbps
      case TelephonyManager.NETWORK_TYPE_HSPA:
        return true; // ~ 700-1700 kbps
      case TelephonyManager.NETWORK_TYPE_HSUPA:
        return true; // ~ 1-23 Mbps
      case TelephonyManager.NETWORK_TYPE_UMTS:
        return true; // ~ 400-7000 kbps
      case TelephonyManager.NETWORK_TYPE_EHRPD:
        return true; // ~ 1-2 Mbps
      case TelephonyManager.NETWORK_TYPE_EVDO_B:
        return true; // ~ 5 Mbps
      case TelephonyManager.NETWORK_TYPE_HSPAP:
        return true; // ~ 10-20 Mbps
      case TelephonyManager.NETWORK_TYPE_IDEN:
        return false; // ~25 kbps
      case TelephonyManager.NETWORK_TYPE_LTE:
        return true; // ~ 10+ Mbps
      case TelephonyManager.NETWORK_TYPE_UNKNOWN:
        return false;
      default:
        return false;
    }
  }

  /**
   * 打开网络设置界面
   */
  public static void openSetting(Context context) {
    if (AndroidVersionUtil.hasHoneycomb()) {
      Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    } else {
      Intent intent = new Intent("/");
      ComponentName cm =
          new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
      intent.setComponent(cm);
      intent.setAction("android.intent.action.VIEW");
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    }
  }
}