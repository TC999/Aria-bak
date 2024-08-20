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
package com.arialyy.annotations;

/**
 * Created by AriaL on 2017/6/14.
 */

public interface AriaConstance {
  String NO_URL = "";

  /**
   * 注解方法为普通任务下载
   */
  int DOWNLOAD = 0xa1;

  /**
   * 注解方法为任务组下载
   */
  int DOWNLOAD_GROUP = 0xa2;

  /**
   * 注解方法为普通任务上传
   */
  int UPLOAD = 0xb1;

  /**
   * 注解方法为任务组上传
   */
  int UPLOAD_GROUP = 0xb2;
}

