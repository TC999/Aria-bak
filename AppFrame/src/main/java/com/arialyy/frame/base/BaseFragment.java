package com.arialyy.frame.base;

import androidx.databinding.ViewDataBinding;
import com.arialyy.frame.core.AbsFragment;

/**
 * Created by Aria.Lao on 2017/12/1.
 */
public abstract class BaseFragment<VB extends ViewDataBinding> extends AbsFragment<VB> {
  public int color;


  @Override protected void dataCallback(int result, Object obj) {

  }

  @Override protected void onDelayLoad() {

  }
}
