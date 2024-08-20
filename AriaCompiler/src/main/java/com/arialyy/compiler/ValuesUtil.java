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
import com.arialyy.annotations.M3U8;
import com.arialyy.annotations.Upload;
import javax.lang.model.element.ExecutableElement;

/**
 * Created by lyy on 2017/9/6.
 * 获取注解value工具
 */
final class ValuesUtil {

  /**
   * 获取m3u8切片注解信息
   */
  static String[] getM3U8PeerValues(ExecutableElement method, int annotationType) {
    String[] values = null;
    switch (annotationType) {
      case ProxyConstance.TASK_START:
        values = method.getAnnotation(M3U8.onPeerStart.class).value();
        break;
      case ProxyConstance.TASK_COMPLETE:
        values = method.getAnnotation(M3U8.onPeerComplete.class).value();
        break;
      case ProxyConstance.TASK_FAIL:
        values = method.getAnnotation(M3U8.onPeerFail.class).value();
        break;
    }
    return values;
  }

  /**
   * 获取下载任务组子任务的的注解数据
   */
  static String[] getDownloadGroupSubValues(ExecutableElement method, int annotationType) {
    String[] values = null;
    switch (annotationType) {
      case ProxyConstance.TASK_PRE:
        values = method.getAnnotation(DownloadGroup.onSubTaskPre.class).value();
        break;
      case ProxyConstance.TASK_START:
        values = method.getAnnotation(DownloadGroup.onSubTaskStart.class).value();
        break;
      case ProxyConstance.TASK_RUNNING:
        values = method.getAnnotation(DownloadGroup.onSubTaskRunning.class).value();
        break;
      case ProxyConstance.TASK_STOP:
        values = method.getAnnotation(DownloadGroup.onSubTaskStop.class).value();
        break;
      case ProxyConstance.TASK_COMPLETE:
        values = method.getAnnotation(DownloadGroup.onSubTaskComplete.class).value();
        break;
      case ProxyConstance.TASK_CANCEL:
        //values = method.getAnnotation(DownloadGroup.onSubTaskCancel.class).value();
        break;
      case ProxyConstance.TASK_FAIL:
        values = method.getAnnotation(DownloadGroup.onSubTaskFail.class).value();
        break;
    }
    return values;
  }

  /**
   * 获取下载任务组的注解数据
   */
  static String[] getDownloadGroupValues(ExecutableElement method, int annotationType) {
    String[] values = null;
    switch (annotationType) {
      case ProxyConstance.PRE:
        values = method.getAnnotation(DownloadGroup.onPre.class).value();
        break;
      case ProxyConstance.TASK_PRE:
        values = method.getAnnotation(DownloadGroup.onTaskPre.class).value();
        break;
      case ProxyConstance.TASK_RESUME:
        values = method.getAnnotation(DownloadGroup.onTaskResume.class).value();
        break;
      case ProxyConstance.TASK_START:
        values = method.getAnnotation(DownloadGroup.onTaskStart.class).value();
        break;
      case ProxyConstance.TASK_RUNNING:
        values = method.getAnnotation(DownloadGroup.onTaskRunning.class).value();
        break;
      case ProxyConstance.TASK_STOP:
        values = method.getAnnotation(DownloadGroup.onTaskStop.class).value();
        break;
      case ProxyConstance.TASK_COMPLETE:
        values = method.getAnnotation(DownloadGroup.onTaskComplete.class).value();
        break;
      case ProxyConstance.TASK_CANCEL:
        values = method.getAnnotation(DownloadGroup.onTaskCancel.class).value();
        break;
      case ProxyConstance.TASK_FAIL:
        values = method.getAnnotation(DownloadGroup.onTaskFail.class).value();
        break;
    }
    return values;
  }

  /**
   * 获取上传的注解数据
   */
  static String[] getUploadValues(ExecutableElement method, int annotationType) {
    String[] values = null;
    switch (annotationType) {
      case ProxyConstance.PRE:
        values = method.getAnnotation(Upload.onPre.class).value();
        break;
      case ProxyConstance.TASK_PRE:
        //values = method.getAnnotation(Upload.onTaskPre.class).value();
        break;
      case ProxyConstance.TASK_RESUME:
        values = method.getAnnotation(Upload.onTaskResume.class).value();
        break;
      case ProxyConstance.TASK_START:
        values = method.getAnnotation(Upload.onTaskStart.class).value();
        break;
      case ProxyConstance.TASK_RUNNING:
        values = method.getAnnotation(Upload.onTaskRunning.class).value();
        break;
      case ProxyConstance.TASK_STOP:
        values = method.getAnnotation(Upload.onTaskStop.class).value();
        break;
      case ProxyConstance.TASK_COMPLETE:
        values = method.getAnnotation(Upload.onTaskComplete.class).value();
        break;
      case ProxyConstance.TASK_CANCEL:
        values = method.getAnnotation(Upload.onTaskCancel.class).value();
        break;
      case ProxyConstance.TASK_FAIL:
        values = method.getAnnotation(Upload.onTaskFail.class).value();
        break;
      case ProxyConstance.TASK_NO_SUPPORT_BREAKPOINT:
        //values = method.getAnnotation(Upload.onNoSupportBreakPoint.class).value();
        break;
    }
    return values;
  }

  /**
   * 获取下载的注解数据
   */
  static String[] getDownloadValues(ExecutableElement method, int annotationType) {
    String[] values = null;
    switch (annotationType) {
      case ProxyConstance.PRE:
        values = method.getAnnotation(Download.onPre.class).value();
        break;
      case ProxyConstance.TASK_PRE:
        values = method.getAnnotation(Download.onTaskPre.class).value();
        break;
      case ProxyConstance.TASK_RESUME:
        values = method.getAnnotation(Download.onTaskResume.class).value();
        break;
      case ProxyConstance.TASK_START:
        values = method.getAnnotation(Download.onTaskStart.class).value();
        break;
      case ProxyConstance.TASK_RUNNING:
        values = method.getAnnotation(Download.onTaskRunning.class).value();
        break;
      case ProxyConstance.TASK_STOP:
        values = method.getAnnotation(Download.onTaskStop.class).value();
        break;
      case ProxyConstance.TASK_COMPLETE:
        values = method.getAnnotation(Download.onTaskComplete.class).value();
        break;
      case ProxyConstance.TASK_CANCEL:
        values = method.getAnnotation(Download.onTaskCancel.class).value();
        break;
      case ProxyConstance.TASK_FAIL:
        values = method.getAnnotation(Download.onTaskFail.class).value();
        break;
      case ProxyConstance.TASK_NO_SUPPORT_BREAKPOINT:
        values = method.getAnnotation(Download.onNoSupportBreakPoint.class).value();
        break;
    }
    return values;
  }
}
