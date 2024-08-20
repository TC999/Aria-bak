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
 * Created by lyy on 2019/6/25.
 * M3U8 切片事件
 * <pre>
 *   <code>
 *      {@literal @}M3U8.onPeerStart
 *       protected void onPeerStart(String m3u8Url, String peerPath, int peerIndex) {
 *          ...
 *       }
 *   </code>
 * </pre>
 */
@Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) public @interface M3U8 {

  /**
   * "@M3U8.onPeerStart"注解，切片开始下载
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onPeerStart {
    String[] value() default { AriaConstance.NO_URL };
  }

  /**
   * "@M3U8.onPeerFail注解，切片下载失败
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onPeerFail {
    String[] value() default { AriaConstance.NO_URL };
  }

  /**
   * "@M3U8.onPeersComplete"注解，切片下载完成
   */
  @Retention(RetentionPolicy.CLASS) @Target(ElementType.METHOD) @interface onPeerComplete {
    String[] value() default { AriaConstance.NO_URL };
  }
}
