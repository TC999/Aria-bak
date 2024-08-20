package com.arialyy.frame.module;

import android.util.SparseIntArray;

import com.arialyy.frame.util.ObjUtil;
import com.arialyy.frame.util.show.L;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by lyy on 2016/7/7.
 */
public class ModuleFactory {
  private Map<Set<Integer>, AbsModule.OnCallback> mPool = new HashMap<>();
  private SparseIntArray mKeyIndex = new SparseIntArray();
  private static final String TAG = "ModuleFactory";

  public ModuleFactory() {

  }

  public void addCallback(int key, AbsModule.OnCallback callback) {
    if (checkKey(key, callback)) {
      L.e(TAG, "key 已经和 callback对应");
      return;
    }
    if (mPool.containsValue(callback)) {
      Set<Integer> oldKeys = ObjUtil.getKeyByValue(mPool, callback);
      if (oldKeys != null) {
        if (!oldKeys.contains(key)) {
          oldKeys.add(key);
          mKeyIndex.put(key, callback.hashCode());
        }
      } else {
        oldKeys = new HashSet<>();
        oldKeys.add(key);
        mPool.put(oldKeys, callback);
        mKeyIndex.put(key, callback.hashCode());
      }
    } else {
      Set<Integer> newKeys = new HashSet<>();
      newKeys.add(key);
      mPool.put(newKeys, callback);
      mKeyIndex.put(key, callback.hashCode());
    }
  }

  /**
   * 检查key和callback的对应关系
   *
   * @return true : key已经和value对应，false : key没有和value对应
   */
  private boolean checkKey(int key, AbsModule.OnCallback callback) {
    return mKeyIndex.indexOfKey(key) != -1 || mKeyIndex.indexOfValue(callback.hashCode()) != -1
        && mKeyIndex.valueAt(callback.hashCode()) == key;
  }
}
