package com.arialyy.frame.temp;

import android.view.View;

public interface OnTempBtClickListener {
  /**
   * @param type {@link ITempView#ERROR}, {@link ITempView#DATA_NULL}, {@link ITempView#LOADING}
   */
  public void onBtTempClick(View view, int type);
}