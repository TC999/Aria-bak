package com.arialyy.frame.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.fragment.app.Fragment;
import com.arialyy.frame.util.AndroidVersionUtil;
import com.arialyy.frame.util.show.L;
import com.arialyy.frame.util.show.T;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyy on 2016/4/11.
 * 6.0权限帮助工具
 */
@TargetApi(Build.VERSION_CODES.M) class PermissionUtil {
  public static final Object LOCK = new Object();
  public volatile static PermissionUtil INSTANCE = null;
  private static final String TAG = "PermissionUtil";

  public static PermissionUtil getInstance() {
    if (INSTANCE == null) {
      synchronized (LOCK) {
        INSTANCE = new PermissionUtil();
      }
    }
    return INSTANCE;
  }

  private PermissionUtil() {
  }

  /**
   * 申请权限
   */
  public void requestPermission(Object obj, int requestCode, String... permission) {
    if (!AndroidVersionUtil.hasM()) {
      return;
    }
    requestPermission(obj, requestCode, "", permission);
  }

  /**
   * 申请权限
   *
   * @param hint 如果框对话框包含”不再询问“选择框的时候的提示用语。
   */
  public void requestPermission(Object obj, int requestCode, String hint, String... permission) {
    if (!AndroidVersionUtil.hasM() || permission == null || permission.length == 0) {
      return;
    }
    Activity activity = null;
    Fragment fragment = null;
    if (obj instanceof Activity) {
      activity = (Activity) obj;
    } else if (obj instanceof Fragment) {
      fragment = (Fragment) obj;
      activity = fragment.getActivity();
    } else {
      L.e(TAG, "obj 只能是 Activity 或者 fragment 及其子类");
      return;
    }
    if (!TextUtils.isEmpty(hint)) {
      for (String str : permission) {
        if (fragment != null) {
          if (fragment.shouldShowRequestPermissionRationale(str)) {
            T.showLong(fragment.getContext(), hint);
            break;
          }
        } else {
          if (activity.shouldShowRequestPermissionRationale(str)) {
            T.showLong(activity, hint);
            break;
          }
        }
      }
    }
    if (fragment != null) {
      fragment.requestPermissions(permission, requestCode);
    } else {
      activity.requestPermissions(permission, requestCode);
    }
  }

  protected String[] list2Array(List<String> denyPermission) {
    String[] array = new String[denyPermission.size()];
    for (int i = 0, count = denyPermission.size(); i < count; i++) {
      array[i] = denyPermission.get(i);
    }
    return array;
  }

  /**
   * 检查没有被授权的权限
   */
  public List<String> checkPermission(Object obj, String... permission) {
    if (!AndroidVersionUtil.hasM() || permission == null || permission.length == 0) {
      return null;
    }
    Activity activity = null;
    if (obj instanceof Activity) {
      activity = (Activity) obj;
    } else if (obj instanceof Fragment) {
      activity = ((Fragment) obj).getActivity();
    } else {
      L.e(TAG, "obj 只能是 Activity 或者 fragment 及其子类");
      return null;
    }
    List<String> denyPermissions = new ArrayList<>();
    for (String p : permission) {
      if (activity.checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
        denyPermissions.add(p);
      }
    }
    return denyPermissions;
  }

  /**
   * 检查应用是否有该权限
   *
   * @param permission 权限，Manifest.permission.CAMERA
   * @return true ==> 已经授权
   */
  public boolean checkPermission(Activity activity, String permission) {
    return AndroidVersionUtil.hasM()
        && activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
  }

  /*
   * 请求悬浮权限
   * 在onActivityResult里面添加以下代码
   * protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   *      super.onActivityResult(requestCode, resultCode, data);
   *      if (requestCode == OnPermissionCallback.PERMISSION_ALERT_WINDOW) {
   *          if (Settings.canDrawOverlays(this)) {       //在这判断是否请求权限成功
   *              Log.i(LOGTAG, "onActivityResult granted");
   *          }
   *      }
   * }
   *
   * @param obj
   */
  public void requestAlertWindowPermission(Object obj) {
    if (!AndroidVersionUtil.hasM()) {
      return;
    }
    Activity activity = null;
    Fragment fragment = null;
    if (obj instanceof Activity) {
      activity = (Activity) obj;
    } else if (obj instanceof Fragment) {
      fragment = (Fragment) obj;
      activity = fragment.getActivity();
    } else {
      L.e(TAG, "obj 只能是 Activity 或者 fragment 及其衍生类");
      return;
    }
    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
    intent.setData(Uri.parse("package:" + activity.getPackageName()));
    if (fragment != null) {
      fragment.startActivityForResult(intent, OnPermissionCallback.PERMISSION_ALERT_WINDOW);
    } else {
      activity.startActivityForResult(intent, OnPermissionCallback.PERMISSION_ALERT_WINDOW);
    }
  }

  /**
   * 请求修改系统设置权限
   */
  public void requestWriteSetting(Object obj) {
    if (!AndroidVersionUtil.hasM()) {
      return;
    }
    Activity activity = null;
    Fragment fragment = null;
    if (obj instanceof Activity) {
      activity = (Activity) obj;
    } else if (obj instanceof Fragment) {
      fragment = (Fragment) obj;
      activity = fragment.getActivity();
    } else {
      L.e(TAG, "obj 只能是 Activity 或者 fragment 及其衍生类");
      return;
    }
    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
    intent.setData(Uri.parse("package:" + activity.getPackageName()));
    if (fragment != null) {
      fragment.startActivityForResult(intent, OnPermissionCallback.PERMISSION_WRITE_SETTING);
    } else {
      activity.startActivityForResult(intent, OnPermissionCallback.PERMISSION_WRITE_SETTING);
    }
  }
}
