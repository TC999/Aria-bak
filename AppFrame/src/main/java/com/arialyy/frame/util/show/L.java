package com.arialyy.frame.util.show;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * Created by Lyy on 2015/4/1.
 * 普通的Log管理类
 */
public class L {
  /**
   * Drawing toolbox
   */
  private static final char TOP_LEFT_CORNER = '╔';
  private static final char BOTTOM_LEFT_CORNER = '╚';
  private static final char MIDDLE_CORNER = '╟';
  private static final char HORIZONTAL_DOUBLE_LINE = '║';
  private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
  private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
  private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
  private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
  private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;
  private static final char I = 'I', W = 'W', D = 'D', E = 'E', V = 'V', A = 'A', M = 'M';

  static String LINE_SEPARATOR = System.getProperty("line.separator"); //等价于"\n\r"，唯一的作用是能装逼
  static int JSON_INDENT = 4;

  private L() {
    /* cannot be instantiated */
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
  private static final String TAG = "lyy";

  /**
   * map to str
   */
  public static String m2s(Map map) {
    if (isDebug) {
      if (map == null) {
        return "";
      }
      StringBuilder sb = new StringBuilder();
      Set set = map.entrySet();
      for (Object aSet : set) {
        Map.Entry entry = (Map.Entry) aSet;
        sb.append(entry.getValue());
      }
      return sb.toString();
    }
    return "";
  }

  /**
   * 打印MAp
   */
  public static void m(Map map) {
    if (isDebug) {
      Set set = map.entrySet();
      if (set.size() < 1) {
        printLog(D, "[]");
        return;
      }

      int i = 0;
      String[] s = new String[set.size()];
      for (Object aSet : set) {
        Map.Entry entry = (Map.Entry) aSet;
        s[i] = entry.getKey() + " = " + entry.getValue() + ",\n";
        i++;
      }
      printLog(V, s);
    }
  }

  /**
   * 打印JSON
   */
  public static void j(String jsonStr) {
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

      message = LINE_SEPARATOR + message;
      String[] lines = message.split(LINE_SEPARATOR);
      printLog(D, lines);
    }
  }

  // 下面四个是默认tag的函数
  public static void i(String... msg) {
    if (isDebug) {
      printLog(I, msg);
    }
  }

  public static void d(String... msg) {
    if (isDebug) {
      printLog(D, msg);
    }
  }

  public static void w(String... msg) {
    if (isDebug) {
      printLog(W, msg);
    }
  }

  public static void e(String... msg) {
    if (isDebug) {
      printLog(E, msg);
    }
  }

  public static void v(String... msg) {
    if (isDebug) {
      printLog(V, msg);
    }
  }

  // 下面是传入自定义tag的函数
  public static void i(String tag, String msg) {
    if (isDebug) {
      Log.i(tag, msg);
    }
  }

  public static void d(String tag, String msg) {
    if (isDebug) {
      Log.d(tag, msg);
    }
  }

  public static void w(String tag, String msg) {
    if (isDebug) {
      Log.w(tag, msg);
    }
  }

  public static void e(String tag, String msg) {
    if (isDebug) {
      Log.e(tag, msg);
    }
  }

  public static void v(String tag, String msg) {
    if (isDebug) {
      Log.v(tag, msg);
    }
  }

  //带异常的
  public static void i(String tag, String msg, Throwable tr) {
    if (isDebug) {
      Log.i(tag, msg, tr);
    }
  }

  public static void d(String tag, String msg, Throwable tr) {
    if (isDebug) {
      Log.d(tag, msg, tr);
    }
  }

  public static void w(String tag, String msg, Throwable tr) {
    if (isDebug) {
      Log.w(tag, msg, tr);
    }
  }

  public static void e(String tag, String msg, Throwable tr) {
    if (isDebug) {
      Log.e(tag, msg, tr);
    }
  }

  public static void v(String tag, String msg, Throwable tr) {
    if (isDebug) {
      Log.v(tag, msg, tr);
    }
  }

  /**
   * 同意打印
   */
  private static void printHunk(char type, String str) {
    switch (type) {
      case I:
        Log.i(TAG, str);
        break;
      case D:
        Log.d(TAG, str);
        break;
      case E:
        Log.e(TAG, str);
        break;
      case V:
        Log.v(TAG, str);
        break;
      case A:
        Log.wtf(TAG, str);
        break;
      case W:
        Log.w(TAG, str);
        break;
    }
  }

  /**
   * 打印头部信息
   */
  private static void printHead(char type) {
    printHunk(type, TOP_BORDER);
    printHunk(type, HORIZONTAL_DOUBLE_LINE + "   Thread:");
    printHunk(type, HORIZONTAL_DOUBLE_LINE + "   " + Thread.currentThread().getName());
    printHunk(type, MIDDLE_BORDER);
  }

  /**
   * 打印Log被调用的位置
   */
  private static void printLocation(char type, String... msg) {
    StackTraceElement[] stack = Thread.currentThread().getStackTrace();
    int i = 0;
    for (StackTraceElement e : stack) {
      String name = e.getClassName();
      if (!name.equals(L.class.getName())) {
        i++;
      } else {
        break;
      }
    }
    i += 3;
    String className = stack[i].getFileName();
    String methodName = stack[i].getMethodName();
    int lineNumber = stack[i].getLineNumber();
    StringBuilder sb = new StringBuilder();
    printHunk(type, HORIZONTAL_DOUBLE_LINE + "   Location:");
    sb.append(HORIZONTAL_DOUBLE_LINE)
        .append("   (")
        .append(className)
        .append(":")
        .append(lineNumber)
        .append(")# ")
        .append(methodName);
    printHunk(type, sb.toString());
    printHunk(type, msg == null || msg.length == 0 ? BOTTOM_BORDER : MIDDLE_BORDER);
  }

  /**
   * 打印消息
   */
  private static void printMsg(char type, String... msg) {
    printHunk(type, HORIZONTAL_DOUBLE_LINE + "   msg:");
    for (String str : msg) {
      printHunk(type, HORIZONTAL_DOUBLE_LINE + "   " + str);
    }
    printHunk(type, BOTTOM_BORDER);
  }

  /**
   * 打印log
   */
  private static void printLog(char type, String... msg) {
    printHead(type);
    printLocation(type, msg);
    if (msg == null || msg.length == 0) {
      return;
    }
    printMsg(type, msg);
  }
}