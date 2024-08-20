package com.arialyy.frame.temp;

/**
 * Created by lyy on 2016/4/27.
 */
public interface ITempView {
  public static final int ERROR = 0xaff1;
  public static final int DATA_NULL = 0xaff2;
  public static final int LOADING = 0xaff3;

  /**
   * 设置填充界面类型
   *
   * @param type {@link ITempView#ERROR}
   * {@link ITempView#DATA_NULL}
   * {@link ITempView#LOADING}
   */
  public void setType(int type);

  /**
   * 在这处理type = ITempView#ERROR 时的逻辑
   */
  public void onError();

  /**
   * 在这处理type = ITempView#DATA_NULL 时的逻辑
   */
  public void onNull();

  /**
   * 在这处理type = ITempView#LOADING 时的逻辑
   */
  public void onLoading();
}
