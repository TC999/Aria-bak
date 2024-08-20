package com.arialyy.frame.util;

import java.math.BigDecimal;

/**
 * Created by AriaLyy on 2015/1/4.
 * 精度转换
 */
public class MathUtil {

  /**
   * http://spiritfrog.iteye.com/blog/602144
   */
  public class MBigDecimal {
    public static final int ROUND_UP = 0;
    public static final int ROUND_DOWN = 1;
    public static final int ROUND_CEILING = 2;
    public static final int ROUND_FLOOR = 3;
    public static final int ROUND_HALF_UP = 4;
    public static final int ROUND_HALF_DOWN = 5;
    public static final int ROUND_HALF_EVEN = 6;
    public static final int ROUND_UNNECESSARY = 7;
  }

  /**
   * 设置精度
   * float/double的精度取值方式分为以下几种: <br>
   * java.math.BigDecimal.ROUND_UP <br>
   * java.math.BigDecimal.ROUND_DOWN <br>
   * java.math.BigDecimal.ROUND_CEILING <br>
   * java.math.BigDecimal.ROUND_FLOOR <br>
   * java.math.BigDecimal.ROUND_HALF_UP<br>
   * java.math.BigDecimal.ROUND_HALF_DOWN <br>
   * java.math.BigDecimal.ROUND_HALF_EVEN <br>
   *
   * @param scale 精度位数(保留的小数位数)
   * @param roundingMode 精度取值方式
   * @return 精度计算后的数据
   */
  public static double round(double value, int scale, int roundingMode) {
    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(scale, roundingMode);
    double d = bd.doubleValue();
    bd = null;
    return d;
  }
}
