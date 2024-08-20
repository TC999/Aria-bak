package com.arialyy.frame.http.inf;

import androidx.annotation.NonNull;

/**
 * 网络请求类
 */
public interface IRequest<T> {
  /**
   * post请求
   *
   * @param url 请求连接
   * @param param 请求参数
   * @param headers 请求头信息
   * @param usCache 是否使用缓存
   * @param body 请求主体
   * @param response 回调接口
   */
  public void post(@NonNull String url, T param, T headers, boolean usCache, byte[] body,
      @NonNull IResponse response);

  /**
   * get请求
   *
   * @param url 请求连接
   * @param param 请求参数
   * @param headers 请求头信息
   * @param usCache 是否使用缓存
   * @param body 请求主体
   * @param response 回调接口
   */
  public void get(@NonNull String url, T param, T headers, boolean usCache, byte[] body,
      @NonNull IResponse response);

  /**
   * 默认缓存文件夹
   */
  public static final String NET_CACHE_DIR = "HttpCache";
}