package com.arialyy.frame.core;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.arialyy.frame.module.AbsModule;
import com.arialyy.frame.module.IOCProxy;
import com.arialyy.frame.util.StringUtil;

/**
 * Created by lyy on 2015/11/4.
 * AlertDialog基类，具有5.0效果，需要配合 AlertDialog.Builder使用
 */
public abstract class AbsAlertDialog extends DialogFragment {
  protected String TAG = "";

  private Object mObj;    //被观察者
  private IOCProxy mProxy;
  private DialogSimpleModule mSimpleModule;
  private Dialog mDialog;
  private ModuleFactory mModuleF;

  public AbsAlertDialog() {
    this(null);
  }

  /**
   * @param obj 被观察的对象
   */
  public AbsAlertDialog(Object obj) {
    mObj = obj;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initDialog();
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return mDialog;
  }

  /**
   * 创建AlertDialog
   * 建议使用AppCompatDialog，该Dialog具有5.0的效果
   */
  public abstract Dialog initAlertDialog();

  private void initDialog() {
    TAG = StringUtil.getClassName(this);
    mProxy = IOCProxy.newInstance(this);
    if (mObj != null) {
      mSimpleModule = new DialogSimpleModule(getContext());
      IOCProxy.newInstance(mObj, mSimpleModule);
    }
    mModuleF = ModuleFactory.newInstance();
    mDialog = initAlertDialog();
  }

  /**
   * 获取Module
   *
   * @param clazz {@link AbsModule}
   */
  protected <M extends AbsModule> M getModule(Class<M> clazz) {
    M module = mModuleF.getModule(getContext(), clazz);
    mProxy.changeModule(module);
    return module;
  }

  /**
   * 获取Module
   *
   * @param clazz Module class0
   * @param callback Module回调函数
   * @param <M> {@link AbsModule}
   */
  protected <M extends AbsModule> M getModule(@NonNull Class<M> clazz,
      @NonNull AbsModule.OnCallback callback) {
    M module = mModuleF.getModule(getContext(), clazz);
    module.setCallback(callback);
    mProxy.changeModule(module);
    return module;
  }

  /**
   * 获取简单打Moduel回调，这个一般用于回调数据给寄主
   */
  protected DialogSimpleModule getSimplerModule() {
    if (mObj == null) {
      throw new NullPointerException("必须设置寄主对象");
    }
    return mSimpleModule;
  }

  protected abstract void dataCallback(int result, Object obj);

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionHelp.getInstance().handlePermissionCallback(requestCode, permissions, grantResults);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    PermissionHelp.getInstance()
        .handleSpecialPermissionCallback(getContext(), requestCode, resultCode, data);
  }
}
