package com.arialyy.frame.core;

import android.content.Context;

import com.arialyy.frame.module.AbsModule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyy on 2015/8/31.
 * Module共享工厂
 */
public class ModuleFactory {

  private final String TAG = "ModuleFactory";

  private Map<Integer, AbsModule> mModules = new HashMap<>();

  private ModuleFactory() {

  }

  /**
   * 需要保证每个对象都有独立的享元工厂
   */
  public static ModuleFactory newInstance() {
    return new ModuleFactory();
  }

  /**
   * 获取Module
   */
  public <M extends AbsModule> M getModule(Context context, Class<M> clazz) {
    M module = (M) mModules.get(clazz.hashCode());
    if (module == null) {
      return newInstanceModule(context, clazz);
    }
    return module;
  }

  /**
   * 构造一个新的Module
   */
  private <T extends AbsModule> T newInstanceModule(Context context, Class<T> clazz) {
    Class[] paramTypes = { Context.class };
    Object[] params = { context };
    try {
      Constructor<T> con = clazz.getConstructor(paramTypes);
      T module = con.newInstance(params);
      mModules.put(clazz.hashCode(), module);
      return module;
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }
}
