package com.arialyy.frame.module;

import android.content.Context;
import androidx.databinding.ViewDataBinding;

import com.arialyy.frame.core.AbsActivity;
import com.arialyy.frame.core.BindingFactory;
import com.arialyy.frame.module.inf.ModuleListener;
import com.arialyy.frame.util.StringUtil;
import com.arialyy.frame.util.show.L;

/**
 * Created by AriaLyy on 2015/2/3.
 * 抽象的module
 */
public class AbsModule {
  public String TAG = "";
  private Context mContext;
  private ModuleListener mModuleListener;
  private OnCallback mCallback;
  private BindingFactory mBindingFactory;
  private Object mHost;

  public interface OnCallback {
    public void onSuccess(int result, Object success);

    public void onError(int result, Object error);
  }

  public AbsModule(Context context) {
    mContext = context;
    init();
  }

  /**
   * 初始化一些东西
   */
  private void init() {
    TAG = StringUtil.getClassName(this);
    mBindingFactory = BindingFactory.newInstance();
  }

  /**
   * 设置Module监听
   *
   * @param moduleListener Module监听
   */
  public void setModuleListener(ModuleListener moduleListener) {
      if (moduleListener == null) {
          throw new NullPointerException("ModuleListener不能为空");
      }
    this.mModuleListener = moduleListener;
  }

  /**
   * 为Binding设置寄主
   */
  public void setHost(Object host) {
    mHost = host;
  }

  /**
   * 成功的回调
   */
  private void successCallback(int key, Object obj) {
    if (mCallback == null) {
      L.e(TAG, "OnCallback 为 null");
      return;
    }
    mCallback.onSuccess(key, obj);
  }

  /**
   * 失败的回调
   */
  public void errorCallback(int key, Object obj) {
    if (mCallback == null) {
      L.e(TAG, "OnCallback 为 null");
      return;
    }
    mCallback.onError(key, obj);
  }

  /**
   * 获取Context
   *
   * @return Context
   */
  public Context getContext() {
    return mContext;
  }

  /**
   * 设置Module回调
   *
   * @param callback Module 回调
   */
  public void setCallback(OnCallback callback) {
    mCallback = callback;
  }

  /**
   * 获取ViewDataBinding
   *
   * @param clazz ViewDataBinding实例
   */
  protected <VB extends ViewDataBinding> VB getBinding(Class<VB> clazz) {
    return mBindingFactory.getBinding(mHost, clazz);
  }

  /**
   * 统一的回调，如果已经设置了OnCallback，则使用OnCallback;
   * 否则将使用dataCallback，{@link AbsActivity#dataCallback(int, Object)}
   *
   * @param result 返回码
   * @param data 回调数据
   */
  protected void callback(final int result, final Object data) {
    if (mCallback != null) {
      successCallback(result, data);
      return;
    }
    mModuleListener.callback(result, data);
  }

  /**
   * module回调
   *
   * @param method 回调的方法名
   */
  @Deprecated
  protected void callback(String method) {
    mModuleListener.callback(method);
  }

  /**
   * 带参数的module回调
   *
   * @param method 回调的方法名
   * @param dataClassType 回调数据类型
   * @param data 回调数据
   */
  @Deprecated
  protected void callback(String method, Class<?> dataClassType, Object data) {
    mModuleListener.callback(method, dataClassType, data);
  }
}
