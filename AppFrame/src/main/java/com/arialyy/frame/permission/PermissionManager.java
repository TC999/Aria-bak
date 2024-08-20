package com.arialyy.frame.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lyy on 2016/4/11.
 * 权限管理工具类
 */
@TargetApi(Build.VERSION_CODES.M) public class PermissionManager implements OnPermissionCallback {
  private static final String TAG = "PermissionManager";
  private PermissionUtil mPu;
  private SparseArray<OnPermissionCallback> mCallbacks = new SparseArray<>();
  private static volatile PermissionManager INSTANCE = null;
  private static final Object LOCK = new Object();

  public static PermissionManager getInstance() {
    if (INSTANCE == null) {
      synchronized (LOCK) {
        INSTANCE = new PermissionManager();
      }
    }
    return INSTANCE;
  }

  private PermissionManager() {
    mPu = PermissionUtil.getInstance();
  }

  /**
   * 检查权限
   *
   * @param permission 需要检查的权限
   * @return true:已经授权
   */
  public boolean checkPermission(Activity activity, String permission) {
    return mPu.checkPermission(activity, permission);
  }

  /**
   * 检查权限
   *
   * @param permission 需要检查的权限
   * @return true:已经授权
   */
  public boolean checkPermission(Fragment fragment, String permission) {
    Activity activity = fragment.getActivity();
    return checkPermission(activity, permission);
  }

  /**
   * 申请悬浮框权限
   *
   * @param obj obj 只能是Activity、Fragment 的子类及其衍生类
   */
  public void requestAlertWindowPermission(Object obj, OnPermissionCallback callback) {
    int hashCode = Arrays.hashCode(new String[] { Settings.ACTION_MANAGE_OVERLAY_PERMISSION });
    registerCallback(callback, hashCode);
    mPu.requestAlertWindowPermission(obj);
  }

  /**
   * 申请修改系统设置权限
   *
   * @param obj obj 只能是Activity、Fragment 的子类及其衍生类
   */
  public void requestWriteSettingPermission(Object obj, OnPermissionCallback callback) {
    int hashCode = Arrays.hashCode(new String[] { Settings.ACTION_MANAGE_WRITE_SETTINGS });
    registerCallback(callback, hashCode);
    mPu.requestWriteSetting(obj);
  }

  /**
   * 申请权限
   *
   * @param obj Activity || Fragment
   * @param permission 权限
   */
  public PermissionManager requestPermission(Object obj, OnPermissionCallback callback,
      String... permission) {
    requestPermissionAndHint(obj, callback, "", registerCallback(obj, callback, permission));
    return this;
  }

  /**
   * 申请权限
   *
   * @param obj Activity || Fragment
   * @param hint 如果框对话框包含“不再询问”选择框的时候的提示用语。
   * @param permission 权限
   */
  public void requestPermissionAndHint(Object obj, OnPermissionCallback callback, String hint,
      String... permission) {
    mPu.requestPermission(obj, 0, hint, registerCallback(obj, callback, permission));
  }

  private void registerCallback(OnPermissionCallback callback, int hashCode) {
    OnPermissionCallback c = mCallbacks.get(hashCode);
    if (c == null) {
      mCallbacks.append(hashCode, callback);
    }
  }

  private String[] registerCallback(Object obj, OnPermissionCallback callback,
      String... permission) {
    List<String> list = mPu.checkPermission(obj, permission);
    if (list == null || list.size() == 0) {
      return null;
    }
    String[] denyPermission = mPu.list2Array(list);
    int hashCode = Arrays.hashCode(denyPermission);
    OnPermissionCallback c = mCallbacks.get(hashCode);
    if (c == null) {
      mCallbacks.append(hashCode, callback);
    }
    return denyPermission;
  }

  @Override public void onSuccess(String... permissions) {
    int hashCode = Arrays.hashCode(permissions);
    OnPermissionCallback c = mCallbacks.get(hashCode);
    if (c != null) {
      c.onSuccess(permissions);
      mCallbacks.remove(hashCode);
    }
  }

  @Override public void onFail(String... permissions) {
    int hashCode = Arrays.hashCode(permissions);
    OnPermissionCallback c = mCallbacks.get(hashCode);
    if (c != null) {
      c.onFail(permissions);
      mCallbacks.remove(hashCode);
    }
  }
}
