package com.arialyy.frame.core;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.annotation.NonNull;
import com.arialyy.frame.module.AbsModule;
import com.arialyy.frame.module.IOCProxy;
import com.arialyy.frame.util.StringUtil;

/**
 * Created by lyy on 2015/12/3.
 * 抽象的Popupwindow悬浮框
 */
public abstract class AbsPopupWindow extends PopupWindow {

  protected String TAG;
  private Context mContext;
  private Drawable mBackground;
  protected View mView;
  private Object mObj;
  protected IOCProxy mProxy;
  protected DialogSimpleModule mSimpleModule;
  private ModuleFactory mModuleF;

  public AbsPopupWindow(Context context) {
    this(context, null);
  }

  public AbsPopupWindow(Context context, Drawable background) {
    this(context, background, null);
  }

  public AbsPopupWindow(Context context, Drawable background, Object obj) {
    mContext = context;
    mBackground = background;
    initPopupWindow();
    mProxy = IOCProxy.newInstance(this);
    if (obj != null) {
      mObj = obj;
      mSimpleModule = new DialogSimpleModule(getContext());
      IOCProxy.newInstance(mObj, mSimpleModule);
    }
    mModuleF = ModuleFactory.newInstance();
    init();
  }

  protected void init() {

  }

  private void initPopupWindow() {
    mView = LayoutInflater.from(mContext).inflate(setLayoutId(), null);
    setContentView(mView);
    TAG = StringUtil.getClassName(this);
    // 设置SelectPicPopupWindow弹出窗体的宽
    setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    //// 设置SelectPicPopupWindow弹出窗体的高
    setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    setFocusable(true);
    // 设置SelectPicPopupWindow弹出窗体动画效果
    //        setAnimationStyle(R.style.wisdom_anim_style);
    // 实例化一个ColorDrawable颜色为半透明
    if (mBackground == null) {
      mBackground = new ColorDrawable(Color.parseColor("#4f000000"));
    }
    // 设置SelectPicPopupWindow弹出窗体的背景
    setBackgroundDrawable(mBackground);
  }

  protected <T extends View> T getViewWithTag(Object tag) {
    T result = (T) mView.findViewWithTag(tag);
    if (result == null) throw new NullPointerException("没有找到tag为【" + tag + "】的控件");
    return result;
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

  public Context getContext() {
    return mContext;
  }

  /**
   * 设置资源布局
   */
  protected abstract int setLayoutId();

  /**
   * 数据回调
   */
  protected abstract void dataCallback(int result, Object obj);
}
