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
package com.arialyy.aria.core.queue.pool;

/**
 * Created by Aria.Lao on 2017/7/17.
 * 下载任务池，该池子为简单任务和任务组共用
 */
public class DLoadSharePool {
  private static volatile DLoadSharePool INSTANCE;

  public DLoadExecutePool executePool;
  public BaseCachePool cachePool;

  private DLoadSharePool() {
    executePool = new DLoadExecutePool<>();
    cachePool = new BaseCachePool<>();
  }

  public static DLoadSharePool getInstance() {
    if (INSTANCE == null) {
      synchronized (DLoadSharePool.class) {
        INSTANCE = new DLoadSharePool();
      }
    }
    return INSTANCE;
  }
}
