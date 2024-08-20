package com.arialyy.frame.util.show;

import android.util.Log;

import com.arialyy.frame.util.CalendarUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created by Lyy on 2015/4/1.
 * 写入文件的log，由于使用到反射和文件流的操作，建议在需要的地方才去使用
 */
public class FL {
  static String LINE_SEPARATOR = System.getProperty("line.separator"); //等价于"\n\r"，唯一的作用是能装逼
  static int JSON_INDENT = 4;
  public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
  public static String NAME = "AriaFrame";    //log路径

  private static String printLine(String tag, boolean isTop) {
    String top =
        "╔══════════════════════════════════════════ JSON ═══════════════════════════════════════";
    String bottom =
        "╚═══════════════════════════════════════════════════════════════════════════════════════";
    if (isTop) {
      Log.d(tag, top);
      return top;
    } else {
      Log.d(tag, bottom);
      return bottom;
    }
  }

  /**
   * 打印JSON
   */
  public static void j(String tag, String jsonStr) {
    if (isDebug) {
      String message;
      try {
        if (jsonStr.startsWith("{")) {
          JSONObject jsonObject = new JSONObject(jsonStr);
          message = jsonObject.toString(JSON_INDENT); //这个是核心方法
        } else if (jsonStr.startsWith("[")) {
          JSONArray jsonArray = new JSONArray(jsonStr);
          message = jsonArray.toString(JSON_INDENT);
        } else {
          message = jsonStr;
        }
      } catch (JSONException e) {
        message = jsonStr;
      }

      writeLogToFile(tag, printLine(tag, true));
      message = LINE_SEPARATOR + message;
      String temp = "";
      String[] lines = message.split(LINE_SEPARATOR);
      for (String line : lines) {
        temp += "║ " + line;
        Log.d(tag, "║ " + line);
      }
      writeLogToFile(tag, temp);
      writeLogToFile(tag, printLine(tag, false));
    }
  }

  // 下面四个是默认tag的函数
  public static void i(Object obj, String msg) {
    String TAG = getTag(obj);
    if (isDebug) {
      Log.i(TAG, msg);
      writeLogToFile(TAG, msg);
    }
  }

  public static void d(Object obj, String msg) {
    String TAG = getTag(obj);
    if (isDebug) {
      Log.d(TAG, msg);
      writeLogToFile(TAG, msg);
    }
  }

  public static void e(Object obj, String msg) {
    String TAG = getTag(obj);
    if (isDebug) {
      Log.e(TAG, msg);
      writeLogToFile(TAG, msg);
    }
  }

  public static void v(Object obj, String msg) {
    String TAG = getTag(obj);
    if (isDebug) {
      Log.v(TAG, msg);
      writeLogToFile(TAG, msg);
    }
  }

  public static void i(String TAG, String msg) {
    if (isDebug) {
      Log.i(TAG, msg);
      writeLogToFile(TAG, msg);
    }
  }

  public static void d(String TAG, String msg) {
    if (isDebug) {
      Log.d(TAG, msg);
      writeLogToFile(TAG, msg);
    }
  }

  public static void e(String TAG, String msg) {
    if (isDebug) {
      Log.e(TAG, msg);
      writeLogToFile(TAG, msg);
    }
  }

  public static void v(String TAG, String msg) {
    if (isDebug) {
      Log.v(TAG, msg);
      writeLogToFile(TAG, msg);
    }
  }

  /**
   * 获取类名
   */
  private static String getTag(Object object) {
    Class<?> cls = object.getClass();
    String tag = cls.getName();
    String arrays[] = tag.split("\\.");
    tag = arrays[arrays.length - 1];
    return tag;
  }

  /**
   * 返回日志路径
   */
  public static String getLogPath() {
    String name = NAME + "_" + CalendarUtils.getData() + ".log";
    return android.os.Environment.getExternalStorageDirectory().getPath() + File.separator + name;
  }

  /**
   * 把日志记录到文件
   */
  private static int writeLogToFile(String tag, String message) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(CalendarUtils.getNowDataTime());
    stringBuffer.append("    ");
    stringBuffer.append(tag);
    stringBuffer.append("    ");
    stringBuffer.append(message);
    stringBuffer.append("\n");
    PrintWriter writer = null;
    try {
      writer = new PrintWriter(new FileWriter(getLogPath(), true));
      writer.append(stringBuffer);
      writer.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (writer != null) {
        writer.close();
      }
    }
    return 0;
  }

  /**
   * 将异常信息转换为字符串
   */
  public static String getExceptionString(Throwable ex) {
    StringBuilder err = new StringBuilder();
    err.append("ExceptionDetailed:\n");
    err.append("====================Exception Info====================\n");
    err.append(ex.toString());
    err.append("\n");
    StackTraceElement[] stack = ex.getStackTrace();
    for (StackTraceElement stackTraceElement : stack) {
      err.append(stackTraceElement.toString()).append("\n");
    }
    Throwable cause = ex.getCause();
    if (cause != null) {
      err.append("【Caused by】: ");
      err.append(cause.toString());
      err.append("\n");
      StackTraceElement[] stackTrace = cause.getStackTrace();
      for (StackTraceElement stackTraceElement : stackTrace) {
        err.append(stackTraceElement.toString()).append("\n");
      }
    }
    err.append("===================================================");
    return err.toString();
  }
}
