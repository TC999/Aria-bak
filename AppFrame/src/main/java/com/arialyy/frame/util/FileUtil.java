package com.arialyy.frame.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.arialyy.frame.util.show.FL;
import com.arialyy.frame.util.show.L;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件操作工具 可以创建和删除文件等
 */
public class FileUtil {
  private static final String KB = "KB";
  private static final String MB = "MB";
  private static final String GB = "GB";

  private static final String TAG = "FileUtil";

  //android获取一个用于打开HTML文件的intent
  public static Intent getHtmlFileIntent(String Path) {
    File file = new File(Path);
    Uri uri = Uri.parse(file.toString())
        .buildUpon()
        .encodedAuthority("com.android.htmlfileprovider")
        .scheme("content")
        .encodedPath(file.toString())
        .build();
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setDataAndType(uri, "text/html");
    return intent;
  }

  /**
   * 获取文件夹大小
   */
  public static long getDirSize(String filePath) {
    long size = 0;
    File f = new File(filePath);
    if (f.isDirectory()) {
      File[] files = f.listFiles();
      for (File file : files) {
        if (file.isDirectory()) {
          size += getDirSize(file.getPath());
          continue;
        }
        size += file.length();
      }
    } else {
      size += f.length();
    }
    return size;
  }

  /**
   * 存储bitmap
   */
  public static void saveBitmap(@NonNull String filePath, @NonNull Bitmap bitmap) {
    File file = createFile(filePath);
    try {
      FileOutputStream os = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
      os.flush();
      os.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 格式化文件大小
   *
   * @param size file.length() 获取文件大小
   */
  public static String formatFileSize(double size) {
    double kiloByte = size / 1024;
    if (kiloByte < 1) {
      return size + "B";
    }

    double megaByte = kiloByte / 1024;
    if (megaByte < 1) {
      BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
      return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
    }

    double gigaByte = megaByte / 1024;
    if (gigaByte < 1) {
      BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
      return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
    }

    double teraBytes = gigaByte / 1024;
    if (teraBytes < 1) {
      BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
      return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
    }
    BigDecimal result4 = new BigDecimal(teraBytes);
    return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
  }

  /**
   * 获取文件后缀名
   */
  public static String getFileExtensionName(String fileName) {
    if (TextUtils.isEmpty(fileName)) {
      return "";
    }
    int endP = fileName.lastIndexOf(".");
    return endP > -1 ? fileName.substring(endP + 1, fileName.length()) : "";
  }

  /**
   * 校验文件MD5码
   */
  public static boolean checkMD5(String md5, File updateFile) {
    if (TextUtils.isEmpty(md5) || updateFile == null) {
      L.e(TAG, "MD5 string empty or updateFile null");
      return false;
    }

    String calculatedDigest = getFileMD5(updateFile);
    if (calculatedDigest == null) {
      L.e(TAG, "calculatedDigest null");
      return false;
    }
    return calculatedDigest.equalsIgnoreCase(md5);
  }

  /**
   * 获取文件MD5码
   */
  public static String getFileMD5(File updateFile) {
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      L.e(TAG, "Exception while getting digest", e);
      return null;
    }

    InputStream is;
    try {
      is = new FileInputStream(updateFile);
    } catch (FileNotFoundException e) {
      L.e(TAG, "Exception while getting FileInputStream", e);
      return null;
    }

    byte[] buffer = new byte[8192];
    int read;
    try {
      while ((read = is.read(buffer)) > 0) {
        digest.update(buffer, 0, read);
      }
      byte[] md5sum = digest.digest();
      BigInteger bigInt = new BigInteger(1, md5sum);
      String output = bigInt.toString(16);
      // Fill to 32 chars
      output = String.format("%32s", output).replace(' ', '0');
      return output;
    } catch (IOException e) {
      throw new RuntimeException("Unable to process file for MD5", e);
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        L.e(TAG, "Exception on closing MD5 input stream", e);
      }
    }
  }

  /**
   * 解压缩功能.
   * 将ZIP_FILENAME文件解压到ZIP_DIR目录下.
   *
   * @param zipFile 压缩文件
   * @param folderPath 解压目录
   */
  public static int unZipFile(File zipFile, String folderPath) {
    ZipFile zfile = null;
    try {
      zfile = new ZipFile(zipFile);
      Enumeration zList = zfile.entries();
      ZipEntry ze = null;
      byte[] buf = new byte[1024];
      while (zList.hasMoreElements()) {
        ze = (ZipEntry) zList.nextElement();
        if (ze.isDirectory()) {
          //                    L.d(TAG, "ze.getName() = " + ze.getName());
          String dirstr = folderPath + ze.getName();
          //dirstr.trim();
          dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
          //                    L.d(TAG, "str = " + dirstr);
          File f = new File(dirstr);
          f.mkdir();
          continue;
        }
        //                L.d(TAG, "ze.getName() = " + ze.getName());
        OutputStream os = new BufferedOutputStream(
            new FileOutputStream(getRealFileName(folderPath, ze.getName())));
        InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
        int readLen = 0;
        while ((readLen = is.read(buf)) != -1) {
          os.write(buf, 0, readLen);
        }
        is.close();
        os.close();
      }
      zfile.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return 0;
  }

  /**
   * 给定根目录，返回一个相对路径所对应的实际文件名.
   *
   * @param baseDir 指定根目录
   * @param absFileName 相对路径名，来自于ZipEntry中的name
   * @return java.io.File 实际的文件
   */
  private static File getRealFileName(String baseDir, String absFileName) {
    String[] dirs = absFileName.split("/");
    File ret = new File(baseDir);
    String substr = null;
    if (dirs.length > 1) {
      for (int i = 0; i < dirs.length - 1; i++) {
        substr = dirs[i];
        try {
          //substr.trim();
          substr = new String(substr.getBytes("8859_1"), "GB2312");
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        ret = new File(ret, substr);
      }
      //            L.d("upZipFile", "1ret = " + ret);
      if (!ret.exists()) ret.mkdirs();
      substr = dirs[dirs.length - 1];
      try {
        //substr.trim();
        substr = new String(substr.getBytes("8859_1"), "GB2312");
        //                L.d("upZipFile", "substr = " + substr);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      ret = new File(ret, substr);
      //            L.d("upZipFile", "2ret = " + ret);
      return ret;
    }

    return ret;
  }

  /**
   * 通过流创建文件
   */
  public static void createFileFormInputStream(InputStream is, String path) {
    try {
      FileOutputStream fos = new FileOutputStream(path);
      byte[] buf = new byte[1376];
      while (is.read(buf) > 0) {
        fos.write(buf, 0, buf.length);
      }
      is.close();
      fos.flush();
      fos.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 从文件读取对象
   */
  public static Object readObj(String path) {
    FileInputStream fis = null;
    ObjectInputStream ois = null;
    try {
      fis = new FileInputStream(path);
      ois = new ObjectInputStream(fis);
      return ois.readObject();
    } catch (FileNotFoundException e) {
      FL.e(TAG, FL.getExceptionString(e));
    } catch (IOException e) {
      FL.e(TAG, FL.getExceptionString(e));
    } catch (ClassNotFoundException e) {
      FL.e(TAG, FL.getExceptionString(e));
    } finally {
      try {
        if (fis != null) {
          fis.close();
        }
        if (ois != null) {
          ois.close();
        }
      } catch (IOException e) {
        FL.e(TAG, FL.getExceptionString(e));
      }
    }
    return null;
  }

  /**
   * 存储对象到文件,只有实现了Serailiable接口的对象才能被存储
   */
  public static void writeObj(String path, Object object) {
    FileOutputStream fos = null;
    ObjectOutputStream oos = null;
    File file = new File(path);
    if (!file.getParentFile().exists()) {
      file.mkdirs();
    }
    try {
      fos = new FileOutputStream(path);
      oos = new ObjectOutputStream(fos);
      oos.writeObject(object);
    } catch (FileNotFoundException e) {
      FL.e(TAG, FL.getExceptionString(e));
    } catch (IOException e) {
      FL.e(TAG, FL.getExceptionString(e));
    } finally {
      try {
        if (fos != null) {
          fos.close();
        }
        if (oos != null) {
          oos.close();
        }
      } catch (IOException e) {
        FL.e(TAG, FL.getExceptionString(e));
      }
    }
  }

  /**
   * 创建文件 当文件不存在的时候就创建一个文件，否则直接返回文件
   */
  public static File createFile(String path) {
    File file = new File(path);
    if (!file.getParentFile().exists()) {
      FL.d(TAG, "目标文件所在路径不存在，准备创建……");
      if (!createDir(file.getParent())) {
        FL.d(TAG, "创建目录文件所在的目录失败！文件路径【" + path + "】");
      }
    }
    // 创建目标文件
    try {
      if (!file.exists()) {
        if (file.createNewFile()) {
          FL.d(TAG, "创建文件成功:" + file.getAbsolutePath());
        }
        return file;
      } else {
        return file;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 创建目录 当目录不存在的时候创建文件，否则返回false
   */
  public static boolean createDir(String path) {
    File file = new File(path);
    if (!file.exists()) {
      if (!file.mkdirs()) {
        FL.d(TAG, "创建失败，请检查路径和是否配置文件权限！");
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * 拷贝文件
   */
  public static boolean copy(String fromPath, String toPath) {
    File file = new File(fromPath);
    if (!file.exists()) {
      return false;
    }
    createFile(toPath);
    return copyFile(fromPath, toPath);
  }

  /**
   * 拷贝文件
   */
  private static boolean copyFile(String fromFile, String toFile) {
    InputStream fosfrom = null;
    OutputStream fosto = null;
    try {
      fosfrom = new FileInputStream(fromFile);
      fosto = new FileOutputStream(toFile);
      byte bt[] = new byte[1024];
      int c;
      while ((c = fosfrom.read(bt)) > 0) {
        fosto.write(bt, 0, c);
      }
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    } finally {
      try {
        if (fosfrom != null) {
          fosfrom.close();
        }
        if (fosto != null) {
          fosto.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 删除文件 如果文件存在删除文件，否则返回false
   */
  public static boolean deleteFile(String path) {
    File file = new File(path);
    if (file.exists()) {
      file.delete();
      return true;
    }
    return false;
  }

  /**
   * 递归删除目录下的所有文件及子目录下所有文件
   *
   * @param dir 将要删除的文件目录
   * @return 删除成功返回true，否则返回false,如果文件是空，那么永远返回true
   */
  public static boolean deleteDir(File dir) {
    if (dir == null) {
      return true;
    }
    if (dir.isDirectory()) {
      String[] children = dir.list();
      // 递归删除目录中的子目录下
      for (String aChildren : children) {
        boolean success = deleteDir(new File(dir, aChildren));
        if (!success) {
          return false;
        }
      }
    }
    // 目录此时为空，可以删除
    return dir.delete();
  }

  /**
   * 递归返回文件或者目录的大小（单位:KB）
   * 不建议使用这个方法，有点坑
   * 可以使用下面的方法：http://blog.csdn.net/loongggdroid/article/details/12304695
   */
  private static float getSize(String path, Float size) {
    File file = new File(path);
    if (file.exists()) {
      if (file.isDirectory()) {
        String[] children = file.list();
        for (int fileIndex = 0; fileIndex < children.length; ++fileIndex) {
          float tmpSize =
              getSize(file.getPath() + File.separator + children[fileIndex], size) / 1000;
          size += tmpSize;
        }
      } else if (file.isFile()) {
        size += file.length();
      }
    }
    return size;
  }

  /**
   * 获取apk文件的icon
   *
   * @param path apk文件路径
   */
  public static Drawable getApkIcon(Context context, String path) {
    PackageManager pm = context.getPackageManager();
    PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
    if (info != null) {
      ApplicationInfo appInfo = info.applicationInfo;
      //android有bug，需要下面这两句话来修复才能获取apk图片
      appInfo.sourceDir = path;
      appInfo.publicSourceDir = path;
      //			    String packageName = appInfo.packageName;  //得到安装包名称
      //	            String version=info.versionName;       //得到版本信息
      return pm.getApplicationIcon(appInfo);
    }
    return null;
  }
}
