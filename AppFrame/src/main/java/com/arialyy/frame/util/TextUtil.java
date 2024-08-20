package com.arialyy.frame.util;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本处理工具
 */
public class TextUtil {
  private TextUtil() {

  }

  /**
   * 格式化字符
   */
  public static String decimalFormat(Number scr, String format) {
    return new DecimalFormat(format).format(scr);
  }

  /**
   * 替换字符号(不带空格)
   */
  public static String replaceSymbol(String str) {
    String dest = "";
    if (str != null) {
      // Pattern p = Pattern.compile("\\s*|\t|\r|\n");
      Pattern p = Pattern.compile("\t|\r|\n");
      Matcher m = p.matcher(str);
      dest = m.replaceAll("");
    }
    return dest;
  }

  /**
   * 首字母大写
   */
  public static String firstUpperCase(String str) {
    if (android.text.TextUtils.isEmpty(str)) {
      return null;
    }
    return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
  }

  /**
   * 从文本中读取数据，返回成List对象
   */
  public static List<String> getTextToList(File file) {
    FileInputStream fileInputStream = null;
    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;
    List<String> list = new ArrayList<String>();
    try {
      fileInputStream = new FileInputStream(file);
      inputStreamReader = new InputStreamReader(fileInputStream);
      bufferedReader = new BufferedReader(inputStreamReader);
      String text;
      while ((text = bufferedReader.readLine()) != null) {
        list.add(text);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (fileInputStream != null) {
          fileInputStream.close();
        }
        if (inputStreamReader != null) {
          inputStreamReader.close();
        }
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  /**
   * 从文本中读取数据，返回成List对象
   */
  public static List<String> getTextToList(InputStream inputStream) {
    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;
    List<String> list = new ArrayList<String>();
    try {
      inputStreamReader = new InputStreamReader(inputStream);
      bufferedReader = new BufferedReader(inputStreamReader);
      String text;
      while ((text = bufferedReader.readLine()) != null) {
        list.add(text);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (inputStreamReader != null) {
          inputStreamReader.close();
        }
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  /**
   * 判断一个数是否是整数
   */
  public static boolean isInteger(String numStr) {
    try {
      double parseDouble = Double.parseDouble(numStr);
      return parseDouble % 1 == 0;
    } catch (Exception exception) {
      return false;
    }
  }

  /**
   * 判断一个数是否是大于0的数
   */
  public static boolean isPositiveInteger(String numStr) {
    if (isInteger(numStr)) {
      double parseDouble = Double.parseDouble(numStr);
      if (parseDouble > 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * 格式化数据
   *
   * @param value 需要转换的值
   * @param pattern 小数位数
   */
  public static String decimalFormat(double value, String pattern) {
    DecimalFormat df = new DecimalFormat(pattern);
    return df.format(value);
  }

  /**
   * 格式化数据
   *
   * @param value 需要转换的值
   * @param pattern 小数位数
   */
  public static String decimalFormat(String value, String pattern) {
    DecimalFormat df = new DecimalFormat(pattern);
    return df.format(Double.parseDouble(value));
  }

  /**
   * 格式化数据
   */
  public static String decimalFormat(double value, int scale) {
    return decimalFormat(value, getScalePattern(scale));
  }

  /**
   * 格式化数据
   */
  public static String decimalFormat(String value, int scale) {
    return decimalFormat(value, getScalePattern(scale));
  }

  /**
   * 返回小数位数的匹配
   */
  private static String getScalePattern(int scale) {
    StringBuffer sb = new StringBuffer("#0.");
    if (scale <= 0) {
      sb = new StringBuffer("#");
    }
    for (int i = 0; i < scale; ++i) {
      sb.append("0");
    }
    return sb.toString();
  }

  /**
   * 返回TextView的值，没有或者null返回0
   */
  public static String getViewText(TextView view) {
    if (view == null) {
      return "0";
    }
    boolean empty = android.text.TextUtils.isEmpty(view.getText().toString());
    return empty ? "0" : view.getText().toString();
  }

  /**
   * 替换字符串
   */
  public static String replace(String source, int index, String before, String after) {
    Matcher matcher = Pattern.compile(before).matcher(source);
    for (int counter = 0; matcher.find(); counter++) {
      if (counter == index) {
        return source.substring(0, matcher.start()) + after + source.substring(matcher.end(),
            source.length());
      }
    }
    return source;
  }

  public static String JsonToString(String src) {
    if ("{}".equals(src) || "[]".equals(src)) {
      return "";
    }
    return src;
  }

  /**
   * 去掉空格和特殊字符
   */
  public static String trimString(String str) {
    if (android.text.TextUtils.isEmpty(str)) {
      return "";
    } else {
      return str.trim();
    }
  }
}