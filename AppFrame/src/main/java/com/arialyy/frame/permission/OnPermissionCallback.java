package com.arialyy.frame.permission;

/**
 * Created by lyy on 2016/4/11.
 * 权限回调
 */
public interface OnPermissionCallback {
  public static final int PERMISSION_ALERT_WINDOW = 0xad1;
  public static final int PERMISSION_WRITE_SETTING = 0xad2;

  public void onSuccess(String... permissions);

  public void onFail(String... permissions);
}
