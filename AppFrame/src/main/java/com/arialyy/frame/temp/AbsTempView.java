package com.arialyy.frame.temp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import com.arialyy.frame.util.StringUtil;
import com.arialyy.frame.util.show.L;

/**
 * Created by lyy on 2016/4/27.
 * 抽象的填充类
 */
public abstract class AbsTempView extends LinearLayout implements ITempView {
  private OnTempBtClickListener mBtListener;
  private static String TAG;
  protected int mType = ERROR;

  public AbsTempView(Context context) {
    this(context, null);
  }

  public AbsTempView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView(context);
  }

  private void initView(Context context) {
    View view = LayoutInflater.from(context).inflate(setLayoutId(), this);
    TAG = StringUtil.getClassName(this);
    init();
  }

  protected abstract void init();

  /**
   * 如果界面有按钮，则需要对着按钮的点击事件进行监听
   */
  public void setBtListener(@NonNull OnTempBtClickListener listener) {
    mBtListener = listener;
  }

  protected abstract int setLayoutId();

  /**
   * 将按钮点击事件传递给TempView调用类
   *
   * @param type {@link ITempView}
   */
  protected void onTempBtClick(View view, int type) {
    if (mBtListener != null) {
      mBtListener.onBtTempClick(view, type);
    }
  }

  @Override
  public void setType(int type) {
    mType = type;
    if (type == LOADING) {
      onLoading();
      return;
    }
    if (type == ERROR) {
      onError();
    } else if (type == DATA_NULL) {
      onNull();
    } else {
      L.e(TAG, "类型错误");
    }
  }
}
