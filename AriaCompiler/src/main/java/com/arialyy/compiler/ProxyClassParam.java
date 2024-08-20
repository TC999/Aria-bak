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

import com.arialyy.annotations.TaskEnum;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lyy on 2017/6/7.
 * 代理类参数
 */
class ProxyClassParam {
  /**
   * 代理文件名
   */
  String proxyClassName;
  /**
   * 被代理的类所在的包
   */
  String packageName;
  /**
   * 被代理的类
   */
  String className;
  /**
   * 主任务泛型参数
   */
  TaskEnum mainTaskEnum;
  /**
   * 子任务泛型参数
   */
  EntityInfo subTaskEnum = EntityInfo.NORMAL;

  Set<TaskEnum> taskEnums;
  Map<String, Set<String>> keyMappings = new HashMap<>();
  Map<TaskEnum, Map<Class<? extends Annotation>, MethodInfo>> methods = new HashMap<>();
}
