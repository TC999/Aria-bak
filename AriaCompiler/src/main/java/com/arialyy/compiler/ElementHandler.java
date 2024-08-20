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
import com.arialyy.annotations.TaskEnum;
import com.arialyy.annotations.Upload;
import java.io.IOException;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.util.Elements;

/**
 * Created by lyy on 2017/6/6.
 * 元素处理
 */
class ElementHandler {

  private Filer mFiler;
  private ParamObtainUtil mPbUtil;

  ElementHandler(Filer filer, Elements elements) {
    mFiler = filer;
    mPbUtil = new ParamObtainUtil(elements);
  }

  /**
   * VariableElement 一般代表成员变量
   * ExecutableElement 一般代表类中的方法
   * TypeElement 一般代表代表类
   * PackageElement 一般代表Package
   */
  void handleDownload(RoundEnvironment roundEnv) {
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD, roundEnv, Download.onWait.class, ProxyConstance.WAIT);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD, roundEnv, Download.onNoSupportBreakPoint.class,
        ProxyConstance.TASK_NO_SUPPORT_BREAKPOINT);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD, roundEnv, Download.onPre.class, ProxyConstance.PRE);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD, roundEnv, Download.onTaskCancel.class,
        ProxyConstance.TASK_CANCEL);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD, roundEnv, Download.onTaskComplete.class,
        ProxyConstance.TASK_COMPLETE);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD, roundEnv, Download.onTaskFail.class,
        ProxyConstance.TASK_FAIL);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD, roundEnv, Download.onTaskPre.class,
        ProxyConstance.TASK_PRE);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD, roundEnv, Download.onTaskResume.class,
        ProxyConstance.TASK_RESUME);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD, roundEnv, Download.onTaskRunning.class,
        ProxyConstance.TASK_RUNNING);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD, roundEnv, Download.onTaskStart.class,
        ProxyConstance.TASK_START);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD, roundEnv, Download.onTaskStop.class,
        ProxyConstance.TASK_STOP);
  }

  /**
   * 处理搜索到的下载任务组注解
   */
  void handleDownloadGroup(RoundEnvironment roundEnv) {
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP, roundEnv, DownloadGroup.onWait.class,
        ProxyConstance.WAIT);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP, roundEnv, DownloadGroup.onPre.class,
        ProxyConstance.PRE);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP, roundEnv, DownloadGroup.onTaskCancel.class,
        ProxyConstance.TASK_CANCEL);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP, roundEnv, DownloadGroup.onTaskComplete.class,
        ProxyConstance.TASK_COMPLETE);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP, roundEnv, DownloadGroup.onTaskFail.class,
        ProxyConstance.TASK_FAIL);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP, roundEnv, DownloadGroup.onTaskPre.class,
        ProxyConstance.TASK_PRE);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP, roundEnv, DownloadGroup.onTaskResume.class,
        ProxyConstance.TASK_RESUME);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP, roundEnv, DownloadGroup.onTaskRunning.class,
        ProxyConstance.TASK_RUNNING);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP, roundEnv, DownloadGroup.onTaskStart.class,
        ProxyConstance.TASK_START);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP, roundEnv, DownloadGroup.onTaskStop.class,
        ProxyConstance.TASK_STOP);
  }

  /**
   * 处理搜索到的下载任务组子任务注解
   */
  void handleDownloadGroupSub(RoundEnvironment roundEnv) {
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP_SUB, roundEnv, DownloadGroup.onSubTaskPre.class,
        ProxyConstance.TASK_PRE);
    //mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP_SUB, roundEnv, DownloadGroup.onSubTaskCancel.class,
    //    ProxyConstance.TASK_CANCEL);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP_SUB, roundEnv, DownloadGroup.onSubTaskComplete.class,
        ProxyConstance.TASK_COMPLETE);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP_SUB, roundEnv, DownloadGroup.onSubTaskFail.class,
        ProxyConstance.TASK_FAIL);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP_SUB, roundEnv, DownloadGroup.onSubTaskRunning.class,
        ProxyConstance.TASK_RUNNING);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP_SUB, roundEnv, DownloadGroup.onSubTaskStart.class,
        ProxyConstance.TASK_START);
    mPbUtil.saveMethod(TaskEnum.DOWNLOAD_GROUP_SUB, roundEnv, DownloadGroup.onSubTaskStop.class,
        ProxyConstance.TASK_STOP);
  }

  /**
   * 处理搜索到的上传注解
   */
  void handleUpload(RoundEnvironment roundEnv) {
    mPbUtil.saveMethod(TaskEnum.UPLOAD, roundEnv, Upload.onWait.class, ProxyConstance.WAIT);
    mPbUtil.saveMethod(TaskEnum.UPLOAD, roundEnv, Upload.onNoSupportBreakPoint.class,
        ProxyConstance.TASK_NO_SUPPORT_BREAKPOINT);
    mPbUtil.saveMethod(TaskEnum.UPLOAD, roundEnv, Upload.onPre.class, ProxyConstance.PRE);
    mPbUtil.saveMethod(TaskEnum.UPLOAD, roundEnv, Upload.onTaskCancel.class,
        ProxyConstance.TASK_CANCEL);
    mPbUtil.saveMethod(TaskEnum.UPLOAD, roundEnv, Upload.onTaskComplete.class,
        ProxyConstance.TASK_COMPLETE);
    mPbUtil.saveMethod(TaskEnum.UPLOAD, roundEnv, Upload.onTaskFail.class,
        ProxyConstance.TASK_FAIL);
    mPbUtil.saveMethod(TaskEnum.UPLOAD, roundEnv, Upload.onTaskResume.class,
        ProxyConstance.TASK_RESUME);
    mPbUtil.saveMethod(TaskEnum.UPLOAD, roundEnv, Upload.onTaskRunning.class,
        ProxyConstance.TASK_RUNNING);
    mPbUtil.saveMethod(TaskEnum.UPLOAD, roundEnv, Upload.onTaskStart.class,
        ProxyConstance.TASK_START);
    mPbUtil.saveMethod(TaskEnum.UPLOAD, roundEnv, Upload.onTaskStop.class,
        ProxyConstance.TASK_STOP);
  }

  /**
   * 处理搜索到到m3u8切片注解
   */
  void handleM3U8(RoundEnvironment roundEnv) {
    mPbUtil.saveMethod(TaskEnum.M3U8_PEER, roundEnv, M3U8.onPeerStart.class,
        ProxyConstance.TASK_START);
    mPbUtil.saveMethod(TaskEnum.M3U8_PEER, roundEnv, M3U8.onPeerComplete.class,
        ProxyConstance.TASK_COMPLETE);
    mPbUtil.saveMethod(TaskEnum.M3U8_PEER, roundEnv, M3U8.onPeerFail.class,
        ProxyConstance.TASK_FAIL);
  }

  /**
   * 在build文件夹中生成代理文件
   */
  void createProxyFile() {
    try {
      new EventProxyFiler(mFiler, mPbUtil).createEventProxyFile();
      //new CountFiler(mFiler, mPbUtil).createCountFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void clean() {
    mPbUtil.getMethodParams().clear();
  }
}
