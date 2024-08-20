package com.arialyy.frame.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import com.arialyy.frame.module.AbsModule;
import com.arialyy.frame.module.IOCProxy;
import com.arialyy.frame.util.StringUtil;
import com.lyy.frame.R;

/**
 * Created by lyy on 2015/11/4.
 * DialogFragment
 */
public abstract class AbsDialogFragment<VB extends ViewDataBinding> extends DialogFragment {
  protected String TAG = "";
  private VB mBind;
  protected Object mObj;
  protected View mRootView;
  protected IOCProxy mProxy;
  protected DialogSimpleModule mSimpleModule;
  protected AbsActivity mActivity;
  private ModuleFactory mModuleF;

  public AbsDialogFragment() {
    this(null);
  }

  /**
   * @param obj 被观察的对象
   */
  public AbsDialogFragment(Object obj) {
    this(STYLE_NO_TITLE, R.style.MyDialog, obj);
  }

  /**
   * @param style DialogFragment.STYLE_NO_TITLE , STYLE_NO_FRAME; STYLE_NO_FRAME | STYLE_NO_TITLE
   * @param theme Dialog风格
   * @param obj 被观察的对象
   */
  private AbsDialogFragment(int style, int theme, Object obj) {
    setStyle(style, theme);
    mObj = obj;
  }

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mBind = DataBindingUtil.inflate(inflater, setLayoutId(), container, false);
    mRootView = mBind.getRoot();
    initFragment();
    return mRootView;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof AbsActivity) {
      mActivity = (AbsActivity) activity;
    }
  }

  public <T extends View> T findViewById(@IdRes int id) {
    return mRootView.findViewById(id);
  }

  private void initFragment() {
    TAG = StringUtil.getClassName(this);
    mProxy = IOCProxy.newInstance(this);
    mModuleF = ModuleFactory.newInstance();
    if (mObj != null) {
      mSimpleModule = new DialogSimpleModule(getContext());
      IOCProxy.newInstance(mObj, mSimpleModule);
    }
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    init(savedInstanceState);
  }

  protected abstract void init(Bundle savedInstanceState);

  /**
   * 获取简单打Moduel回调，这个一般用于回调数据给寄主
   */
  protected DialogSimpleModule getSimplerModule() {
    if (mObj == null) {
      throw new NullPointerException("必须设置寄主对象");
    }
    return mSimpleModule;
  }

  /**
   * 设置资源布局
   */
  protected abstract int setLayoutId();

  /**
   * 获取binding对象
   */
  protected VB getBinding() {
    return mBind;
  }

  /**
   * 获取Module
   *
   * @param clazz {@link AbsModule}
   */
  protected <M extends AbsModule> M getModule(Class<M> clazz) {
    M module = mModuleF.getModule(getContext(), clazz);
    module.setHost(this);
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
   * 统一的回调接口
   *
   * @param result 返回码，用来判断是哪个接口进行回调
   * @param data 回调数据
   */
  protected abstract void dataCallback(int result, Object data);

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionHelp.getInstance().handlePermissionCallback(requestCode, permissions, grantResults);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    PermissionHelp.getInstance()
        .handleSpecialPermissionCallback(getContext(), requestCode, resultCode, data);
  }
}
