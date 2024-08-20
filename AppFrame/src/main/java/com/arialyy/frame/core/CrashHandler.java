package com.arialyy.frame.core;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;

import com.arialyy.frame.http.HttpUtil;
import com.arialyy.frame.permission.PermissionManager;
import com.arialyy.frame.util.AndroidUtils;
import com.arialyy.frame.util.CalendarUtils;
import com.arialyy.frame.util.FileUtil;
import com.arialyy.frame.util.NetUtils;
import com.arialyy.frame.util.show.FL;
import com.arialyy.frame.util.show.L;
import com.arialyy.frame.util.show.T;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by lyy on 2016/4/21.
 * 异常捕获
 */
final class CrashHandler implements Thread.UncaughtExceptionHandler {
  private static final Object LOCK = new Object();
  private static volatile CrashHandler INSTANCE = null;
  private static final String TAG = "CrashHandler";
  private Thread.UncaughtExceptionHandler mDefaultHandler;
  private Context mContext;
  private String mServerHost, mPramKey;
  private String mExceptionFileName = "AbsExceptionFile.crash";

  /**
   * 获取CrashHandler实例 ,单例模式
   */
  public static CrashHandler getInstance(Context context) {
    if (INSTANCE == null) {
      synchronized (LOCK) {
        INSTANCE = new CrashHandler(context);
      }
    }
    return INSTANCE;
  }

  private CrashHandler(Context context) {
    mContext = context;
    mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
  }

  /**
   * 开启异常捕获
   * 需要网络权限，get请求，异常参数
   *
   * @param serverHost 服务器地址
   * @param key 数据传输键值
   */
  public void setServerHost(String serverHost, String key) {
    mServerHost = serverHost;
    mPramKey = key;
  }

  @Override
  public void uncaughtException(Thread thread, Throwable ex) {
    if (!handleException(ex) && mDefaultHandler != null) {
      mDefaultHandler.uncaughtException(thread, ex);
    } else {
      // Sleep一会后结束程序
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      //不要把线程都杀了，否则连日志都看不了
      //            android.os.Process.killProcess(android.os.Process.myPid());
      //如果把这句话注释掉，有异常都不会退出
      System.exit(10);
    }
  }

  /**
   * 处理捕获到的异常
   */
  private boolean handleException(Throwable ex) {
    if (ex == null) {
      return false;
    }
    //在这里处理崩溃逻辑,将不再显示FC对话框
    new Thread() {
      @Override
      public void run() {
        Looper.prepare();
        T.showLong(mContext, "很抱歉，程序出现异常，即将退出");
        Looper.loop();
      }
    }.start();
    sendExceptionInfo(ex);
    return true;
  }

  /**
   * 发送异常数据给服务器
   */
  private void sendExceptionInfo(final Throwable ex) {
    ExceptionInfo info = new ExceptionInfo();
    info.time = CalendarUtils.getNowDataTime();
    info.versionCode = AndroidUtils.getVersionCode(mContext);
    info.versionName = AndroidUtils.getVersionName(mContext);
    info.systemVersionCode = Build.VERSION.SDK_INT;
    info.phoneModel = Build.MODEL;
    info.exceptionMsg = FL.getExceptionString(ex);
    if (AndroidUtils.checkPermission(mContext, Manifest.permission.INTERNET) &&
        AndroidUtils.checkPermission(mContext, Manifest.permission.ACCESS_NETWORK_STATE)) {
      if (NetUtils.isConnected(mContext) && !TextUtils.isEmpty(mServerHost) && !TextUtils.isEmpty(
          mPramKey)) {
        String objStr = new Gson().toJson(info);
        HttpUtil util = HttpUtil.getInstance(mContext);
        Map<String, String> params = new WeakHashMap<>();
        params.put(mPramKey, objStr);
        util.get(mServerHost, params, new HttpUtil.AbsResponse());
      }
    } else {
      L.e(TAG,
          "请在manifest文件定义android.permission.INTERNET和android.permission.ACCESS_NETWORK_STATE权限");
      return;
    }
    File file = new File(mContext.getCacheDir().getPath() + "/crash/" + mExceptionFileName);
    if (!file.exists()) {
      FileUtil.createFile(file.getPath());
    }
    writeExceptionToFile(info.exceptionMsg, file);
  }

  /**
   * 将异常日志写入文件
   */
  private void writeExceptionToFile(String message, File crashFile) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(CalendarUtils.getNowDataTime());
    stringBuffer.append("\n");
    stringBuffer.append(message);
    stringBuffer.append("\n");
    PrintWriter writer = null;
    try {
      writer = new PrintWriter(new FileWriter(crashFile, true));
      writer.append(stringBuffer);
      writer.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (writer != null) {
        writer.close();
      }
    }
  }

  private class ExceptionInfo {
    int versionCode;
    String versionName;
    int systemVersionCode;
    String exceptionMsg;
    String phoneModel;
    String time;
  }
}
