package com.arialyy.frame.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * 应用签名信息
 * Created by YangQiang on 2014/11/27.
 */
public class AppSigning {
  public final static String MD5 = "MD5";
  public final static String SHA1 = "SHA1";
  public final static String SHA256 = "SHA256";

  /**
   * 返回一个签名的对应类型的字符串
   */
  public static ArrayList<String> getSingInfo(Context context, String packageName, String type) {
    ArrayList<String> result = new ArrayList<String>();
    try {
      Signature[] signs = getSignatures(context, packageName);
      for (Signature sig : signs) {
        String tmp = "error!";
        if (MD5.equals(type)) {
          tmp = getSignatureString(sig, MD5);
        } else if (SHA1.equals(type)) {
          tmp = getSignatureString(sig, SHA1);
        } else if (SHA256.equals(type)) {
          tmp = getSignatureString(sig, SHA256);
        }
        result.add(tmp);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 返回对应包的签名信息
   */
  public static Signature[] getSignatures(Context context, String packageName) {
    PackageInfo packageInfo = null;
    try {
      packageInfo =
          context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
      return packageInfo.signatures;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 获取相应的类型的字符串（把签名的byte[]信息转换成16进制）
   */
  public static String getSignatureString(Signature sig, String type) {
    byte[] hexBytes = sig.toByteArray();
    String fingerprint = "error!";
    try {
      MessageDigest digest = MessageDigest.getInstance(type);
      if (digest != null) {
        byte[] digestBytes = digest.digest(hexBytes);
        StringBuilder sb = new StringBuilder();
        for (byte digestByte : digestBytes) {
          sb.append((Integer.toHexString((digestByte & 0xFF) | 0x100)).substring(1, 3));
        }
        fingerprint = sb.toString();
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return fingerprint;
  }
}
