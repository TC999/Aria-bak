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
import com.google.auto.service.AutoService;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * Created by lyy on 2017/6/6.
 * 事件注解扫描器
 */
@AutoService(Processor.class) public class AriaProcessor extends AbstractProcessor {
  ElementHandler mHandler;

  @Override public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    PrintLog.init(processingEnv.getMessager());
    mHandler = new ElementHandler(processingEnv.getFiler(), processingEnv.getElementUtils());
  }

  @Override public Set<String> getSupportedAnnotationTypes() {
    Set<String> annotataions = new LinkedHashSet<>();
    //单任务下载的注解
    annotataions.add(Download.onWait.class.getCanonicalName());
    annotataions.add(Download.onPre.class.getCanonicalName());
    annotataions.add(Download.onNoSupportBreakPoint.class.getCanonicalName());
    annotataions.add(Download.onTaskCancel.class.getCanonicalName());
    annotataions.add(Download.onTaskComplete.class.getCanonicalName());
    annotataions.add(Download.onTaskFail.class.getCanonicalName());
    annotataions.add(Download.onTaskPre.class.getCanonicalName());
    annotataions.add(Download.onTaskResume.class.getCanonicalName());
    annotataions.add(Download.onTaskRunning.class.getCanonicalName());
    annotataions.add(Download.onTaskStart.class.getCanonicalName());
    annotataions.add(Download.onTaskStop.class.getCanonicalName());
    //下载任务组的注解
    annotataions.add(DownloadGroup.onWait.class.getCanonicalName());
    annotataions.add(DownloadGroup.onPre.class.getCanonicalName());
    annotataions.add(DownloadGroup.onTaskCancel.class.getCanonicalName());
    annotataions.add(DownloadGroup.onTaskComplete.class.getCanonicalName());
    annotataions.add(DownloadGroup.onTaskFail.class.getCanonicalName());
    annotataions.add(DownloadGroup.onTaskPre.class.getCanonicalName());
    annotataions.add(DownloadGroup.onTaskResume.class.getCanonicalName());
    annotataions.add(DownloadGroup.onTaskRunning.class.getCanonicalName());
    annotataions.add(DownloadGroup.onTaskStart.class.getCanonicalName());
    annotataions.add(DownloadGroup.onTaskStop.class.getCanonicalName());
    //任务组子任务的注解
    annotataions.add(DownloadGroup.onSubTaskPre.class.getCanonicalName());
    //annotataions.add(DownloadGroup.onSubTaskCancel.class.getCanonicalName());
    annotataions.add(DownloadGroup.onSubTaskComplete.class.getCanonicalName());
    annotataions.add(DownloadGroup.onSubTaskFail.class.getCanonicalName());
    annotataions.add(DownloadGroup.onSubTaskRunning.class.getCanonicalName());
    annotataions.add(DownloadGroup.onSubTaskStart.class.getCanonicalName());
    annotataions.add(DownloadGroup.onSubTaskStop.class.getCanonicalName());
    //上传任务的注解
    annotataions.add(Upload.onWait.class.getCanonicalName());
    annotataions.add(Upload.onPre.class.getCanonicalName());
    annotataions.add(Upload.onNoSupportBreakPoint.class.getCanonicalName());
    annotataions.add(Upload.onTaskCancel.class.getCanonicalName());
    annotataions.add(Upload.onTaskComplete.class.getCanonicalName());
    annotataions.add(Upload.onTaskFail.class.getCanonicalName());
    annotataions.add(Upload.onTaskResume.class.getCanonicalName());
    annotataions.add(Upload.onTaskRunning.class.getCanonicalName());
    annotataions.add(Upload.onTaskStart.class.getCanonicalName());
    annotataions.add(Upload.onTaskStop.class.getCanonicalName());
    // M3U8切片事件
    annotataions.add(M3U8.onPeerStart.class.getCanonicalName());
    annotataions.add(M3U8.onPeerComplete.class.getCanonicalName());
    annotataions.add(M3U8.onPeerFail.class.getCanonicalName());
    return annotataions;
  }

  @Override public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    mHandler.clean();
    mHandler.handleDownload(roundEnv);
    mHandler.handleDownloadGroup(roundEnv);
    mHandler.handleDownloadGroupSub(roundEnv);
    mHandler.handleUpload(roundEnv);
    mHandler.handleM3U8(roundEnv);
    mHandler.createProxyFile();
    return true;
  }
}
