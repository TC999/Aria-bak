package com.arialyy.frame.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.arialyy.frame.module.AbsModule;
import com.arialyy.frame.module.IOCProxy;
import com.arialyy.frame.temp.AbsTempView;
import com.arialyy.frame.temp.OnTempBtClickListener;
import com.arialyy.frame.temp.TempView;
import com.arialyy.frame.util.StringUtil;
import com.arialyy.frame.util.show.T;

/**
 * Created by lyy on 2015/11/3.
 * 所有的 Activity都应该继承这个类
 */
public abstract class AbsActivity<VB extends ViewDataBinding> extends AppCompatActivity
    implements OnTempBtClickListener {
  protected String TAG = "";
  private VB mBind;
  private IOCProxy mProxy;
  /**
   * 第一次点击返回的系统时间
   */
  private long mFirstClickTime = 0;
  protected AbsFrame mAm;
  protected View mRootView;
  private ModuleFactory mModuleF;
  protected AbsTempView mTempView;
  protected boolean useTempView = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initialization();
    init(savedInstanceState);
  }

  private void initialization() {
    mAm = AbsFrame.getInstance();
    mAm.addActivity(this);
    mBind = DataBindingUtil.setContentView(this, setLayoutId());
    mProxy = IOCProxy.newInstance(this);
    TAG = StringUtil.getClassName(this);
    mModuleF = ModuleFactory.newInstance();
    mRootView = mBind.getRoot();
    if (useTempView) {
      mTempView = new TempView(this);
      mTempView.setBtListener(this);
    }
  }

  /**
   * 获取填充View
   */
  protected AbsTempView getTempView() {
    return mTempView;
  }

  /**
   * 是否使用填充界面
   */
  protected void setUseTempView(boolean useTempView) {
    this.useTempView = useTempView;
  }

  /**
   * 设置自定义的TempView
   */
  protected void setCustomTempView(AbsTempView tempView) {
    mTempView = tempView;
    mTempView.setBtListener(this);
  }

  /**
   * 显示占位布局
   *
   * @param type {@link TempView#ERROR}
   * {@link TempView#DATA_NULL}
   * {@link TempView#LOADING}
   */
  protected void showTempView(int type) {
    if (mTempView == null || !useTempView) {
      return;
    }
    mTempView.setVisibility(View.VISIBLE);
    mTempView.setType(type);
    setContentView(mTempView);
  }

  public ModuleFactory getModuleFactory() {
    return mModuleF;
  }

  /**
   * 关闭占位布局
   */
  protected void hintTempView() {
    hintTempView(0);
  }

  /**
   * 延时关闭占位布局
   */
  protected void hintTempView(int delay) {
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        if (mTempView == null || !useTempView) {
          return;
        }
        mTempView.clearFocus();
        mTempView.setVisibility(View.GONE);
        setContentView(mRootView);
      }
    }, delay);
  }

  @Override
  public void onBtTempClick(View view, int type) {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  protected void init(Bundle savedInstanceState) {

  }

  @Override
  public void finish() {
    super.finish();
    mAm.removeActivity(this);
  }

  public View getRootView() {
    return mRootView;
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
  protected <M extends AbsModule> M getModule(@NonNull Class<M> clazz) {
    M module = mModuleF.getModule(this, clazz);
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
    M module = mModuleF.getModule(this, clazz);
    module.setCallback(callback);
    mProxy.changeModule(module);
    return module;
  }

  /**
   * 数据回调
   */
  protected abstract void dataCallback(int result, Object data);

  /**
   * 双击退出
   */
  private boolean onDoubleClickExit(long timeSpace) {
    long currentTimeMillis = System.currentTimeMillis();
    if (currentTimeMillis - mFirstClickTime > timeSpace) {
      T.showShort(this, "再按一次退出");
      mFirstClickTime = currentTimeMillis;
      return false;
    } else {
      return true;
    }
  }

  /**
   * 双击退出，间隔时间为2000ms
   */
  public boolean onDoubleClickExit() {
    return onDoubleClickExit(2000);
  }

  /**
   * 退出应用程序
   *
   * @param isBackground 是否开开启后台运行,如果为true则为后台运行
   */
  public void exitApp(Boolean isBackground) {
    mAm.exitApp(isBackground);
  }

  /**
   * 退出应用程序
   */
  public void exitApp() {
    mAm.exitApp(false);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionHelp.getInstance().handlePermissionCallback(requestCode, permissions, grantResults);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    PermissionHelp.getInstance()
        .handleSpecialPermissionCallback(this, requestCode, resultCode, data);
  }
}
