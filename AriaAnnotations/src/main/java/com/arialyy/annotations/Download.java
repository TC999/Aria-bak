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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lyy on 2017/6/6.
 * Aria下载事件被注解的方法中，参数仅能有一个，参数类型为 com.arialyy.aria.core.download.DownloadTask
 * <pre>
 *   <code>
 *      {@literal @}Download.onPre
 *       protected void onPre(DownloadTask task) {
 *          mUpdateHandler.obtainMessage(DOWNLOAD_PRE, task.getDownloadEntity().getFileSize()).sendToTarget();
 *       }
 *   </code>
 * </pre>
 */
@Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) public @interface Download {

  /**
   * "@Download.onPre"注解，下载队列已经满了，继续创建新任务，将会回调该方法
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onWait {
    String[] value() default { AriaConstance.NO_URL };
  }

  /**
   * "@Download.onPre"注解，在预处理完成时，Aria会调用该方法
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onPre {
    String[] value() default { AriaConstance.NO_URL };
  }

  /**
   * "@Download.onTaskPre"注解，在任务预处理完成时，Aria会调用该方法
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onTaskPre {
    String[] value() default { AriaConstance.NO_URL };
  }

  /**
   * "@Download.onTaskResume"注解，在任务恢复下载时，Aria会调用该方法
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onTaskResume {
    String[] value() default { AriaConstance.NO_URL };
  }

  /**
   * "@Download.onTaskStart"注解，在任务开始下载时，Aria会调用该方法
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onTaskStart {
    String[] value() default { AriaConstance.NO_URL };
  }

  /**
   * "@Download.onTaskStop"注解，在任务停止时，Aria会调用该方法
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onTaskStop {
    String[] value() default { AriaConstance.NO_URL };
  }

  /**
   * "@Download.onTaskCancel}l注解，在任务取消时，Aria会调用该方法
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onTaskCancel {
    String[] value() default { AriaConstance.NO_URL };
  }

  /**
   * "@Download.onTaskFail)注解，在任务预失败时，Aria会调用该方法
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onTaskFail {
    String[] value() default { AriaConstance.NO_URL };
  }

  /**
   * "@Download.onTaskComplete"注解，在任务完成时，Aria会调用该方法
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onTaskComplete {
    String[] value() default { AriaConstance.NO_URL };
  }

  /**
   * "@Download.onTaskRunning"注解，在任务正在下载，Aria会调用该方法
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onTaskRunning {
    String[] value() default { AriaConstance.NO_URL };
  }

  /**
   * "@Download.onNoSupportBreakPoint"注解，如果该任务不支持断点，Aria会调用该方法
   *
   * @deprecated 该注解将在后续版本删除
   */
  @Deprecated
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onNoSupportBreakPoint {
    String[] value() default { AriaConstance.NO_URL };
  }
}
