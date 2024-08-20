package com.arialyy.frame.cache;

import android.os.Environment;

/**
 * Created by AriaL on 2017/11/26.
 */

public class PathConstaant {
  private static final String WP_DIR = "windPath";

  /**
   * 获取APK升级路径
   */
  public static String getWpPath() {
    return Environment.getExternalStorageDirectory().getPath()
        + "/"
        + WP_DIR
        + "/update/windPath.apk";
  }
}
