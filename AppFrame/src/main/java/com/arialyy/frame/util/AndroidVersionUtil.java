package com.arialyy.frame.util;

import android.os.Build;

/**
 * android版本检测工具
 *
 * @author lyy
 */
public class AndroidVersionUtil {
  private AndroidVersionUtil() {
  }

  /**
   * 当前Android系统版本是否在（ Donut） Android 1.6或以上
   */
  public static boolean hasDonut() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT;
  }

  /**
   * 当前Android系统版本是否在（ Eclair） Android 2.0或 以上
   */
  public static boolean hasEclair() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR;
  }

  /**
   * 当前Android系统版本是否在（ Froyo） Android 2.2或 Android 2.2以上
   */
  public static boolean hasFroyo() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
  }

  /**
   * 当前Android系统版本是否在（ Gingerbread） Android 2.3x或 Android 2.3x 以上
   */
  public static boolean hasGingerbread() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
  }

  /**
   * 当前Android系统版本是否在（ Honeycomb） Android3.1或 Android3.1以上
   */
  public static boolean hasHoneycomb() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  /**
   * 当前Android系统版本是否在（ HoneycombMR1） Android3.1.1或 Android3.1.1以上
   */
  public static boolean hasHoneycombMR1() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
  }

  /**
   * 当前Android系统版本是否在（ IceCreamSandwich） Android4.0或 Android4.0以上
   */
  public static boolean hasIcecreamsandwich() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
  }

  /**
   * 当前android系统版本是否在（JellyBean）Android4.1或android4.1以上
   */
  public static boolean hasJellyBean() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
  }

  /**
   * 当前android系统版本是否在（KitKat）Android4.4或android4.4以上
   */
  public static boolean hasKitKat() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
  }

  /**
   * 当前是否在5.0以上
   */
  public static boolean hasLollipop() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
  }

  /**
   * 当前是否在6.0以上
   */
  public static boolean hasM() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
  }
}
