package com.arialyy.frame.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;

import com.arialyy.frame.util.show.FL;
import com.arialyy.frame.util.show.L;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.PathClassLoader;

/**
 * Created by lyy on 2015/7/30.
 * 反射工具类
 */
public class ReflectionUtil {
  private static final String TAG = "ReflectionUtil";
  private static final String ID = "$id";
  private static final String LAYOUT = "$layout";
  private static final String STYLE = "$style";
  private static final String STRING = "$string";
  private static final String DRAWABLE = "$drawable";
  private static final String ARRAY = "$array";
  private static final String COLOR = "color";
  private static final String ANIM = "anim";

  /**
   * 从SDcard读取layout
   */
  public static XmlPullParser getLayoutXmlPullParser(Context context, String filePath,
      String fileName) {
    XmlResourceParser paser = null;
    AssetManager asset = context.getResources().getAssets();
    try {
      Method method = asset.getClass().getMethod("addAssetPath", String.class);
      int cookie = (Integer) method.invoke(asset, filePath);
      if (cookie == 0) {
        FL.e(TAG, "加载路径失败");
      }
      paser = asset.openXmlResourceParser(cookie, fileName + ".xml");
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return paser;
  }

  /**
   * 获取类里面的所在字段
   */
  public static Field[] getFields(Class clazz) {
    Field[] fields = null;
    fields = clazz.getDeclaredFields();
    if (fields == null || fields.length == 0) {
      Class superClazz = clazz.getSuperclass();
      if (superClazz != null) {
        fields = getFields(superClazz);
      }
    }
    return fields;
  }

  /**
   * 获取类里面的指定对象，如果该类没有则从父类查询
   */
  public static Field getField(Class clazz, String name) {
    Field field = null;
    try {
      field = clazz.getDeclaredField(name);
    } catch (NoSuchFieldException e) {
      try {
        field = clazz.getField(name);
      } catch (NoSuchFieldException e1) {
        if (clazz.getSuperclass() == null) {
          return field;
        } else {
          field = getField(clazz.getSuperclass(), name);
        }
      }
    }
    if (field != null) {
      field.setAccessible(true);
    }
    return field;
  }

  /**
   * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。
   *
   * @param clazz 目标类
   * @param methodName 方法名
   * @param params 方法参数类型数组
   * @return 方法对象
   */
  public static Method getMethod(Class clazz, String methodName, final Class<?>... params) {
    Method method = null;
    try {
      method = clazz.getDeclaredMethod(methodName, params);
    } catch (NoSuchMethodException e) {
      try {
        method = clazz.getMethod(methodName, params);
      } catch (NoSuchMethodException ex) {
        if (clazz.getSuperclass() == null) {
          L.e(TAG, "无法找到" + methodName + "方法");
          FL.e(TAG, FL.getExceptionString(e));
          return method;
        } else {
          method = getMethod(clazz.getSuperclass(), methodName, params);
        }
      }
    }
    if (method != null) {
      method.setAccessible(true);
    }
    return method;
  }

  /**
   * 加载指定的反射类
   */
  public static Class<?> loadClass(Context context, String ClassName) {
    String packageName = AndroidUtils.getPackageName(context);
    String sourcePath = AndroidUtils.getSourcePath(context, packageName);
    if (!TextUtils.isEmpty(sourcePath)) {
      PathClassLoader cl =
          new PathClassLoader(sourcePath, "/data/app/", ClassLoader.getSystemClassLoader());
      try {
        return cl.loadClass(ClassName);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      FL.e(TAG, "没有【" + sourcePath + "】目录");
    }
    return null;
  }
}
