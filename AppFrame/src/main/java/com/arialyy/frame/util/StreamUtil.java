package com.arialyy.frame.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by AriaLyy on 2015/4/10.
 */
public class StreamUtil {
  /**
   * 得到图片字节流 数组大小
   */
  public static byte[] readStream(InputStream inStream) throws Exception {
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len = 0;
    while ((len = inStream.read(buffer)) != -1) {
      outStream.write(buffer, 0, len);
    }
    outStream.close();
    return outStream.toByteArray();
  }
}
