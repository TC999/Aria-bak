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
package com.arialyy.compiler;

/**
 * Created by lyy on 2017/6/7.
 * 扫描器常量
 */
interface ProxyConstance {
  boolean DEBUG = false;
  /**
   * 设置观察者的方法
   */
  String SET_LISTENER = "setListener";

  int WAIT = 0X10;
  int PRE = 0X11;
  int TASK_PRE = 0X12;
  int TASK_RESUME = 0X13;
  int TASK_START = 0X14;
  int TASK_STOP = 0X15;
  int TASK_CANCEL = 0X16;
  int TASK_FAIL = 0X17;
  int TASK_COMPLETE = 0X18;
  int TASK_RUNNING = 0X19;
  int TASK_NO_SUPPORT_BREAKPOINT = 0X1A;
}
