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
package com.arialyy.aria.core.common.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE) public @interface ControllerType {
  /**
   * 仅用于第一次创建任务，后续可调用`#create()、#add()、#setHighestPriority()`方法。
   */
  Class<BuilderController> CREATE_CONTROLLER = BuilderController.class;
  /**
   * 用于后续的任务控制，后续可调用`#stop()、#resume()、#cancel()、#cancel(boolean)、#retry()、#restart()`方法。
   */
  Class<NormalController> TASK_CONTROLLER = NormalController.class;
}
