package com.arialyy.frame.module;

import com.arialyy.frame.module.inf.ModuleListener;
import com.arialyy.frame.util.ReflectionUtil;
import com.arialyy.frame.util.show.FL;
import com.arialyy.frame.util.show.L;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by AriaLyy on 2015/2/3.
 * 代理对象
 */
public class IOCProxy implements ModuleListener {
  private static final String TAG = "IOCProxy";
  private static final String mMethod = "dataCallback";

  private Object mObj;

  /**
   * 初始化静态代理
   */
  public static IOCProxy newInstance(Object obj, AbsModule module) {
    return new IOCProxy(obj, module);
  }

  public static IOCProxy newInstance(Object obj) {
    return new IOCProxy(obj, null);
  }

  /**
   * 被代理对象
   */
  private IOCProxy(Object obj, AbsModule module) {
    this.mObj = obj;

    if (module != null) {
      module.setModuleListener(this);
    }
  }

  public void changeModule(AbsModule module) {
    module.setModuleListener(this);
  }

  /**
   * 统一的数据回调
   *
   * @param result 返回码
   * @param data 回调数据
   */
  @Override
  public void callback(int result, Object data) {
    synchronized (this) {
      try {
        Method m = ReflectionUtil.getMethod(mObj.getClass(), mMethod, int.class, Object.class);
        m.setAccessible(true);
        m.invoke(mObj, result, data);
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 数据回调
   *
   * @param method 方法名
   */
  @Override
  @Deprecated
  public void callback(String method) {
    synchronized (this) {
      try {
        Method m = mObj.getClass().getDeclaredMethod(method);
        m.setAccessible(true);
        m.invoke(mObj);
      } catch (NoSuchMethodException e) {
        L.e(TAG, "无法找到" + method + "方法");
        FL.e(TAG, FL.getExceptionString(e));
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 带参数的回调
   *
   * @param method 方法名
   * @param dataClassType 参数类型,如 int.class
   * @param data 数据
   */
  @Override
  @Deprecated
  public void callback(String method, Class<?> dataClassType, Object data) {
    synchronized (this) {
      try {
        Method m = mObj.getClass().getDeclaredMethod(method, dataClassType);
        m.setAccessible(true);
        m.invoke(mObj, data);
      } catch (NoSuchMethodException e) {
        L.e(TAG, "无法找到" + method + "方法");
        FL.e(TAG, FL.getExceptionString(e));
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }
}
