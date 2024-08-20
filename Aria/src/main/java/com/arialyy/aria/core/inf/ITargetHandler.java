/*
 * Copyright (C) 2016 AriaLyy(https://github.com/AriaLyy/Aria)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arialyy.aria.core.inf;

/**
 * Created by AriaL on 2017/6/29.
 * 任务接收器的控制接口，处理添加任务、开始任务、停止任务、删除任务等功能
 */
public interface ITargetHandler {
  int D_HTTP = 1;
  int U_HTTP = 2;
  int U_FTP = 3;
  int D_FTP = 4;
  //HTTP任务组
  int GROUP_HTTP = 5;
  //FTP文件夹
  int GROUP_FTP_DIR = 6;

  /**
   * 添加任务
   */
  void add();

  /**
   * 开始下载
   */
  void start();

  /**
   * 停止下载
   */
  void stop();

  /**
   * 恢复下载
   */
  void resume();

  /**
   * 取消下载
   */
  void cancel();

  /**
   * 保存修改
   */
  void save();

  /**
   * 删除任务
   *
   * @param removeFile {@code true} 不仅删除任务数据库记录，还会删除已经删除完成的文件
   * {@code false}如果任务已经完成，只删除任务数据库记录，
   */
  void cancel(boolean removeFile);

  /**
   * 任务重试
   */
  void reTry();

  /**
   * 重新下载
   */
  void reStart();
}
