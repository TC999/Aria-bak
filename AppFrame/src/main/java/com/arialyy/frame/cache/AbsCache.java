package com.arialyy.frame.cache;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.collection.LruCache;
import com.arialyy.frame.util.AndroidUtils;
import com.arialyy.frame.util.AppUtils;
import com.arialyy.frame.util.StreamUtil;
import com.arialyy.frame.util.StringUtil;
import com.arialyy.frame.util.show.FL;
import com.arialyy.frame.util.show.L;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Lyy on 2015/4/9.
 * 缓存抽象类，封装了缓存的读写操作
 */
public abstract class AbsCache implements CacheParam {
  private static final String TAG = "AbsCache";
  /**
   * 磁盘缓存工具
   */
  private DiskLruCache mDiskLruCache = null;
  /**
   * 内存缓存工具
   */
  private LruCache<String, byte[]> mMemoryCache = null;
  /**
   * 是否使用内存缓存
   */
  private boolean useMemory = false;
  private int mMaxMemory;
  private Context mContext;
  private static final Object mDiskCacheLock = new Object();

  /**
   * 默认使用默认路径
   *
   * @param useMemory 是否使用内存缓存
   */
  protected AbsCache(Context context, boolean useMemory) {
    this.mContext = context;
    this.useMemory = useMemory;
    init(DEFAULT_DIR, 1, SMALL_DISK_CACHE_CAPACITY);
  }

  /**
   * 指定缓存文件夹
   *
   * @param useMemory 是否使用内存缓存
   * @param cacheDir 缓存文件夹
   */
  protected AbsCache(Context context, boolean useMemory, @NonNull String cacheDir) {
    this.mContext = context;
    this.useMemory = useMemory;
    init(cacheDir, 1, SMALL_DISK_CACHE_CAPACITY);
  }

  private void init(String cacheDir, int valueCount, long cacheSize) {
    initDiskCache(cacheDir, valueCount, cacheSize);
    initMemoryCache();
  }

  /**
   * 初始化磁盘缓存
   */
  protected void initDiskCache(String cacheDir, int valueCount, long cacheSize) {
    try {
      File dir = getDiskCacheDir(mContext, cacheDir);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      mDiskLruCache =
          DiskLruCache.open(dir, AppUtils.getVersionNumber(mContext), valueCount, cacheSize);
    } catch (IOException e) {
      FL.e(this, "createCacheFailed\n" + FL.getExceptionString(e));
    }
  }

  /**
   * 初始化内存缓存
   */
  protected void initMemoryCache() {
    if (!useMemory) {
      return;
    }
    // 获取应用程序最大可用内存
    mMaxMemory = (int) Runtime.getRuntime().maxMemory();
    // 设置图片缓存大小为程序最大可用内存的1/8
    mMemoryCache = new LruCache<>(mMaxMemory / 8);
  }

  /**
   * 是否使用内存缓存
   */
  protected void setUseMemory(boolean useMemory) {
    this.useMemory = useMemory;
    initMemoryCache();
  }

  /**
   * 设置内存缓存大小
   */
  protected void setMemoryCache(int size) {
    mMemoryCache.resize(size);
  }

  /**
   * 打开某个目录下的缓存
   *
   * @param cacheDir 缓存目录，只需填写文件夹名，不需要写路径
   * @param valueCount 指定同一个key可以对应多少个缓存文件，基本都是传1
   * @param cacheSize 缓存大小
   * @see CacheParam
   */
  protected void openDiskCache(@NonNull String cacheDir, int valueCount, long cacheSize) {
    synchronized (mDiskCacheLock) {
      if (mDiskLruCache != null && mDiskLruCache.isClosed()) {
        try {
          File dir = getDiskCacheDir(mContext, cacheDir);
          if (!dir.exists()) {
            dir.mkdirs();
          }
          mDiskLruCache =
              DiskLruCache.open(dir, AppUtils.getVersionNumber(mContext), valueCount, cacheSize);
        } catch (IOException e) {
          FL.e(this, "createCacheFailed\n" + FL.getExceptionString(e));
        }
      }
    }
  }

  /**
   * 把缓存写入磁盘
   *
   * @param key 缓存的key,通过该key来读写缓存，一般是URL
   * @param data 缓存的数据
   */
  protected void writeDiskCache(@NonNull String key, @NonNull byte[] data) {
    if (TextUtils.isEmpty(key)) {
      return;
    }
    String hashKey = StringUtil.keyToHashKey(key);
    if (useMemory && mMemoryCache != null) {
      mMemoryCache.put(hashKey, data);
    }
    synchronized (mDiskCacheLock) {
      if (mDiskLruCache != null) {
        L.i(TAG, "缓存数据到磁盘[key:" + key + ",hashKey:" + hashKey + "]");
        OutputStream out = null;
        try {
          DiskLruCache.Editor editor = mDiskLruCache.edit(hashKey);
          out = editor.newOutputStream(DISK_CACHE_INDEX);
          out.write(data, 0, data.length);
          editor.commit();
          out.flush();
          out.close();
        } catch (IOException e) {
          FL.e(this,
              "writeDiskFailed[key:" + key + ",hashKey:" + hashKey + "]\n" + FL.getExceptionString(
                  e));
        } finally {
          if (out != null) {
            try {
              out.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }

  /**
   * 从磁盘读取缓存
   *
   * @param key 缓存的key，一般是原来的url
   * @return 缓存数据
   */
  protected byte[] readDiskCache(@NonNull String key) {
    if (TextUtils.isEmpty(key)) {
      return null;
    }
    String hashKey = StringUtil.keyToHashKey(key);
    if (useMemory && mMemoryCache != null) {
      final byte[] data = mMemoryCache.get(hashKey);
      if (data != null && data.length != 0) {
        return data;
      }
    }
    synchronized (mDiskCacheLock) {
      byte[] data = null;
      L.i(TAG, "读取磁盘缓存数据[key:" + key + ",hashKey:" + hashKey + "]");
      InputStream inputStream = null;
      try {
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(hashKey);
        if (snapshot != null) {
          inputStream = snapshot.getInputStream(DISK_CACHE_INDEX);
          data = StreamUtil.readStream(inputStream);
          return data;
        }
      } catch (IOException e) {
        FL.e(this, "readDiskCacheFailed[key:"
            + key
            + ",hashKey:"
            + hashKey
            + "]\n"
            + FL.getExceptionString(e));
      } catch (Exception e) {
        FL.e(this, "readDiskCacheFailed[key:"
            + key
            + ",hashKey:"
            + hashKey
            + "]\n"
            + FL.getExceptionString(e));
      } finally {
        if (inputStream != null) {
          try {
            inputStream.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return null;
  }

  /**
   * 删除一条缓存
   *
   * @param key 该缓存的key
   */
  protected void removeCache(@NonNull String key) {
    String hashKey = StringUtil.keyToHashKey(key);
    if (mMemoryCache != null) {
      mMemoryCache.remove(hashKey);
    }
    synchronized (mDiskCacheLock) {
      if (mDiskLruCache != null) {
        try {
          mDiskLruCache.remove(hashKey);
        } catch (IOException e) {
          FL.e(this, "removeCacheFailed[key:"
              + key
              + ",hashKey:"
              + hashKey
              + "]\n"
              + FL.getExceptionString(e));
        }
      }
    }
  }

  /**
   * 清除所有缓存
   */
  protected void clearCache() {
    if (mMemoryCache != null) {
      mMemoryCache.evictAll();
    }
    synchronized (mDiskCacheLock) {
      if (mDiskLruCache != null) {
        try {
          mDiskLruCache.delete();
        } catch (IOException e) {
          FL.e(this, "clearCacheFailed" + FL.getExceptionString(e));
        }
      }
    }
  }

  /**
   * 关闭磁盘缓存，注意：
   * 这个方法用于将DiskLruCache关闭掉，是和open()方法对应的一个方法。
   * 关闭掉了之后就不能再调用DiskLruCache中任何操作缓存数据的方法，
   * 通常只应该在Activity的onDestroy()方法中去调用close()方法。
   */
  protected void closeDiskCache() {
    synchronized (mDiskCacheLock) {
      if (mDiskLruCache != null) {
        try {
          mDiskLruCache.close();
        } catch (IOException e) {
          FL.e(this, "closeDiskCacheFailed" + FL.getExceptionString(e));
        }
      }
    }
  }

  /**
   * 同步内存中的缓存操作记录到日志文件（也就是journal文件）
   * 注意：在写入缓存时需要flush同步一次，并不是每次写入缓存都要调用一次flush()方法的，频繁地调用并不会带来任何好处，
   * 只会额外增加同步journal文件的时间。比较标准的做法就是在Activity的onPause()方法中去调用一次flush()方法就可以了
   */
  protected void flushDiskCache() {
    synchronized (mDiskCacheLock) {
      if (mDiskLruCache != null) {
        try {
          mDiskLruCache.flush();
        } catch (IOException e) {
          FL.e(this, "flushDiskCacheFailed" + FL.getExceptionString(e));
        }
      }
    }
  }

  /**
   * 获取缓存大小
   */
  protected long getCacheSize() {
    return mDiskLruCache.size();
  }

  /**
   * 转换byte数组为String
   */
  private static String bytesToHexString(byte[] bytes) {
    // http://stackoverflow.com/questions/332079
    StringBuilder sb = new StringBuilder();
    for (byte aByte : bytes) {
      String hex = Integer.toHexString(0xFF & aByte);
      if (hex.length() == 1) {
        sb.append('0');
      }
      sb.append(hex);
    }
    return sb.toString();
  }

  /**
   * 生成缓存文件夹
   *
   * @param uniqueName 缓存文件夹名
   * @return 缓存文件夹
   */
  public static File getDiskCacheDir(Context context, String uniqueName) {
    return new File(AndroidUtils.getDiskCacheDir(context) + File.separator + uniqueName);
  }
}
