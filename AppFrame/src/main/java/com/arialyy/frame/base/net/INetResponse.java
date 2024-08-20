package com.arialyy.frame.base.net;

/**
 * Created by “Aria.Lao” on 2016/10/25.
 * 网络响应接口，所有的网络回调都要继承该接口
 *
 * @param <T> 数据实体结构
 */
public interface INetResponse<T> {

  /**
   * 网络请求成功
   */
  public void onResponse(T response);

  /**
   * 请求失败
   */
  public void onFailure(Throwable e);
}
