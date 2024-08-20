package com.arialyy.frame.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * 常用单位转换的辅助类
 */
public class DensityUtils {
  private DensityUtils() {
    /* cannot be instantiated */
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

  /**
   * 另外一种dp转PX方法
   */
  public static int dp2px(int dp) {
    return Math.round(dp * DENSITY);
  }

  /**
   * dp转px
   */
  public static int dp2px(Context context, float dpVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        dpVal, context.getResources().getDisplayMetrics());
  }

  /**
   * sp转px
   */
  public static int sp2px(Context context, float spVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
        spVal, context.getResources().getDisplayMetrics());
  }

  /**
   * px转dp
   */
  public static float px2dp(Context context, float pxVal) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (pxVal / scale);
  }

  /**
   * px转sp
   */
  public static float px2sp(Context context, float pxVal) {
    return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
  }
}