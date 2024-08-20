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

package com.arialyy.simple.common;

import android.text.TextUtils;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.arialyy.aria.util.ALog;
import com.arialyy.frame.base.BaseViewModule;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DialogModule extends BaseViewModule {

  private MutableLiveData<List<File>> mDirs = new MutableLiveData<>();

  /**
   * 获取指定目录下的文件夹
   *
   * @param path 指定路径
   */
  LiveData<List<File>> getDirs(String path) {
    if (TextUtils.isEmpty(path)) {
      ALog.e(TAG, "路径为空");
      return mDirs;
    }
    if (!path.startsWith("/")) {
      ALog.e(TAG, "路径错误");
      return mDirs;
    }
    File file = new File(path);
    File[] files = file.listFiles();
    List<File> data = new ArrayList<>();
    for (File f : files) {
      if (f.isDirectory()) {
        data.add(f);
      }
    }
    mDirs.postValue(data);

    return mDirs;
  }
}
