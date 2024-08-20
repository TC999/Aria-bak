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

import com.arialyy.annotations.Download;
import com.arialyy.annotations.DownloadGroup;
import com.arialyy.annotations.TaskEnum;
import com.arialyy.annotations.Upload;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by lyy on 2017/9/6.
 * 构建代理文件的参数获取工具
 */
class ParamObtainUtil {
  private Map<String, ProxyClassParam> mMethodParams = new HashMap<>();
  private Elements mElementUtil;

  ParamObtainUtil(Elements elements) {
    mElementUtil = elements;
  }

  /**
   * 获取搜索到的代理方法参数
   */
  Map<String, ProxyClassParam> getMethodParams() {
    return mMethodParams;
  }

  /**
   * 查找并保存扫描到的方法
   */
  void saveMethod(TaskEnum taskEnum, RoundEnvironment roundEnv,
      Class<? extends Annotation> annotationClazz, int annotationType) {
    for (Element element : roundEnv.getElementsAnnotatedWith(annotationClazz)) {
      ElementKind kind = element.getKind();
      if (kind == ElementKind.METHOD) {
        ExecutableElement method = (ExecutableElement) element;
        TypeElement classElement = (TypeElement) method.getEnclosingElement();
        PackageElement packageElement = mElementUtil.getPackageOf(classElement);

        String methodName = method.getSimpleName().toString();
        String className = method.getEnclosingElement().toString(); //全类名
        String key = className + taskEnum.proxySuffix;
        ProxyClassParam proxyEntity = mMethodParams.get(key);
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.methodName = methodName;
        methodInfo.params = (List<VariableElement>) method.getParameters();
        if (taskEnum == TaskEnum.M3U8_PEER) {
          checkM3U8PeerMethod(method, methodInfo.params);
        } else {
          checkTaskMethod(taskEnum, method, annotationClazz, methodInfo.params);
        }

        if (proxyEntity == null) {
          proxyEntity = new ProxyClassParam();
          proxyEntity.taskEnums = new HashSet<>();
          proxyEntity.packageName = packageElement.getQualifiedName().toString();
          proxyEntity.className = classElement.getSimpleName().toString();
          proxyEntity.proxyClassName = proxyEntity.className + taskEnum.proxySuffix;
          proxyEntity.mainTaskEnum = taskEnum;
          if (taskEnum == TaskEnum.DOWNLOAD_GROUP_SUB || taskEnum == TaskEnum.DOWNLOAD_GROUP) {
            proxyEntity.subTaskEnum = EntityInfo.DOWNLOAD;
          }
          mMethodParams.put(key, proxyEntity);
        }
        proxyEntity.taskEnums.add(taskEnum);
        if (proxyEntity.methods.get(taskEnum) == null) {
          proxyEntity.methods.put(taskEnum, new HashMap<Class<? extends Annotation>, MethodInfo>());
        }

        proxyEntity.methods.get(taskEnum).put(annotationClazz, methodInfo);
        proxyEntity.keyMappings.put(methodName, getValues(taskEnum, method, annotationType));
      }
    }
  }

  /**
   * 获取注解的内容
   */
  private Set<String> getValues(TaskEnum taskEnum, ExecutableElement method, int annotationType) {
    String[] keys = null;
    switch (taskEnum) {
      case DOWNLOAD:
        keys = ValuesUtil.getDownloadValues(method, annotationType);
        break;
      case UPLOAD:
        keys = ValuesUtil.getUploadValues(method, annotationType);
        break;
      case DOWNLOAD_GROUP:
        keys = ValuesUtil.getDownloadGroupValues(method, annotationType);
        break;
      case DOWNLOAD_GROUP_SUB:
        keys = ValuesUtil.getDownloadGroupSubValues(method, annotationType);
        break;
      case M3U8_PEER:
        keys = ValuesUtil.getM3U8PeerValues(method, annotationType);
        break;
    }
    return keys == null ? null : convertSet(keys);
  }

  /**
   * 检查m3u8注解方法参数
   */
  private void checkM3U8PeerMethod(ExecutableElement method, List<VariableElement> params) {
    String methodName = method.getSimpleName().toString();
    String className = method.getEnclosingElement().toString();
    Set<Modifier> modifiers = method.getModifiers();
    if (modifiers.contains(Modifier.PRIVATE)) {
      throw new IllegalAccessError(String.format("%s.%s, 不能为private方法", className, methodName));
    }

    if (params.size() != 3
        || !params.get(0).asType().toString().equals(String.class.getName())
        || !params.get(1).asType().toString().equals(String.class.getName())
        || !params.get(2).asType().toString().equals("int")) {
      throw new IllegalArgumentException(
          String.format("%s.%s 的参数错误，该方法需要的参数为：String, String, int", className,
              methodName));
    }
  }

  /**
   * 检查任务注解的相关参数，如果被注解的方法为private或参数不合法或参数错误，停止任务
   */
  private void checkTaskMethod(TaskEnum taskEnum, ExecutableElement method,
      Class<? extends Annotation> annotationClazz, List<VariableElement> params) {
    String methodName = method.getSimpleName().toString();
    String className = method.getEnclosingElement().toString();
    Set<Modifier> modifiers = method.getModifiers();
    if (modifiers.contains(Modifier.PRIVATE)) {
      throw new IllegalAccessError(String.format("%s.%s, 不能为private方法", className, methodName));
    }
    if (taskEnum == TaskEnum.DOWNLOAD_GROUP_SUB) {
      if (isFailAnnotation(annotationClazz)) {
        if (params.size() < 2 || params.size() > 3) {
          throw new IllegalArgumentException(
              String.format("%s.%s参数错误, 参数只能是两个或三个，第一个参数是：%s，第二个参数是：%s，第三个参数（可选）是：%s", className,
                  methodName,
                  getCheckParams(taskEnum), getCheckSubParams(taskEnum),
                  Exception.class.getSimpleName()));
        }

        if (params.size() == 2
            && (!params.get(0).asType().toString().equals(getCheckParams(taskEnum))
            || !params.get(1).asType().toString().equals(getCheckSubParams(taskEnum)))) {
          throw new IllegalArgumentException(
              String.format("%s.%s参数错误, 参数只能是两个或三个，第一个参数是：%s，第二个参数是：%s，第三个参数（可选）是：%s", className,
                  methodName,
                  getCheckParams(taskEnum), getCheckSubParams(taskEnum),
                  Exception.class.getSimpleName()));
        }

        if (params.size() == 3
            && (!params.get(0).asType().toString().equals(getCheckParams(taskEnum))
            || !params.get(1).asType().toString().equals(getCheckSubParams(taskEnum))
            || !params.get(2).asType().toString().equals(Exception.class.getName()))) {
          throw new IllegalArgumentException(
              String.format("%s.%s参数错误, 参数只能是两个或三个，第一个参数是：%s，第二个参数是：%s，第三个参数（可选）是：%s", className,
                  methodName,
                  getCheckParams(taskEnum), getCheckSubParams(taskEnum),
                  Exception.class.getSimpleName()));
        }
      } else {
        if (params.size() == 2
            && (!params.get(0).asType().toString().equals(getCheckParams(taskEnum))
            || !params.get(1).asType().toString().equals(getCheckSubParams(taskEnum)))) {
          throw new IllegalArgumentException(
              String.format("%s.%s参数错误, 参数只能是两个或三个，第一个参数是：%s，第二个参数是：%s", className,
                  methodName,
                  getCheckParams(taskEnum), getCheckSubParams(taskEnum)));
        }
      }
    } else {
      if (isFailAnnotation(annotationClazz)) {
        if (params.size() < 1 || params.size() > 2) {
          throw new IllegalArgumentException(
              String.format("%s.%s参数错误, 参数只能有一个或两个，第一个参数是：%s，第二个参数（可选）是：%s", className, methodName,
                  getCheckParams(taskEnum), Exception.class.getSimpleName()));
        }

        if (params.size() == 1
            && (!params.get(0).asType().toString().equals(getCheckParams(taskEnum)))) {
          throw new IllegalArgumentException(
              String.format("%s.%s参数错误, 参数只能有一个或两个，第一个参数是：%s，第二个参数（可选）是：%s", className, methodName,
                  getCheckParams(taskEnum), Exception.class.getSimpleName()));
        }

        if (params.size() == 2
            && (!params.get(0).asType().toString().equals(getCheckParams(taskEnum))
            || !params.get(1).asType().toString().equals(Exception.class.getName()))) {
          throw new IllegalArgumentException(
              String.format("%s.%s参数错误, 参数只能有一个或两个，第一个参数是：%s，第二个参数（可选）是：%s", className, methodName,
                  getCheckParams(taskEnum), Exception.class.getSimpleName()));
        }
      } else {
        if (params.size() != 1
            && !params.get(0).asType().toString().equals(getCheckParams(taskEnum))) {
          throw new IllegalArgumentException(
              String.format("%s.%s参数错误, 参数只能有一个，且参数必须是：%s", className, methodName,
                  getCheckParams(taskEnum)));
        }
      }
    }
  }

  /**
   * 判断是否是任务失败的回调注解
   *
   * @return ｛@code true｝是任务失败的回调注解
   */
  private boolean isFailAnnotation(Class<? extends Annotation> annotationClazz) {
    return annotationClazz == Download.onTaskFail.class
        || annotationClazz == DownloadGroup.onTaskFail.class
        || annotationClazz == DownloadGroup.onSubTaskFail.class
        || annotationClazz == Upload.onTaskFail.class;
  }

  /**
   * 字符串数组转set
   *
   * @param keys 注解中查到的key
   */
  private Set<String> convertSet(final String[] keys) {
    if (keys == null || keys.length == 0) {
      return null;
    }
    if (keys[0].isEmpty()) return null;
    Set<String> set = new HashSet<>();
    Collections.addAll(set, keys);
    return set;
  }

  private String getCheckParams(TaskEnum taskEnum) {
    return taskEnum.pkg + "." + taskEnum.className;
  }

  /**
   * 检查任务组子任务第二个参数
   */
  private String getCheckSubParams(TaskEnum taskEnum) {
    if (taskEnum == TaskEnum.DOWNLOAD_GROUP_SUB) {
      return EntityInfo.DOWNLOAD.pkg + "." + EntityInfo.DOWNLOAD.className;
    }
    return "";
  }
}
