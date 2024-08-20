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
package com.arialyy.simple.core.upload;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.frame.util.FileUtil;
import com.arialyy.frame.util.show.T;
import com.arialyy.simple.R;
import java.io.File;

/**
 * Created by lyy on 2017/7/28. HTTP 文件下载demo
 * <a href="https://aria.laoyuyu.me/aria_doc/">文档</>
 */
public class FtpUpload extends Activity {
  private static final String TAG = "FtpUpload";
  private String mFilePath = Environment.getExternalStorageDirectory().getPath() + "/test.apk";
  private String mUrl = "http://hzdown.muzhiwan.com/2017/05/08/nl.noio.kingdom_59104935e56f0.apk";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Aria.download(this).register();
    // 读取历史记录信息
    DownloadEntity entity = Aria.download(this).getDownloadEntity(mFilePath);
    if (entity != null) {
      // 设置界面的进度、文件大小等信息
    }
  }

  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.start:
        if (Aria.download(this).load(mUrl).isRunning()) {
          Aria.download(this).load(mUrl).stop(); // 停止任务
        } else {
          Aria.download(this)
              .load(mUrl) // 下载url
              .setFilePath(mFilePath) // 文件保存路径
              //.addHeader(key, value) // 添加头
              //.asPost() //或 asGet()
              //.setParam() // 设置参数
              .create();
        }
        break;
      case R.id.cancel:
        Aria.download(this).load(mUrl).cancel();
        break;
    }
  }

  @Download.onWait void onWait(DownloadTask task) {
    Log.d(TAG, task.getTaskName() + "_wait");
  }

  @Download.onPre public void onPre(DownloadTask task) {
    setFileSize(task.getConvertFileSize());
  }

  @Download.onTaskStart public void taskStart(DownloadTask task) {
    Log.d(TAG, "开始下载，md5：" + FileUtil.getFileMD5(new File(task.getEntity().getFilePath())));
  }

  @Download.onTaskResume public void taskResume(DownloadTask task) {
    Log.d(TAG, "恢复下载");
  }

  @Download.onTaskStop public void taskStop(DownloadTask task) {
    setSpeed("");
    Log.d(TAG, "停止下载");
  }

  @Download.onTaskCancel public void taskCancel(DownloadTask task) {
    setSpeed("");
    setFileSize("");
    setProgress(0);
    Log.d(TAG, "删除任务");
  }

  @Download.onTaskFail public void taskFail(DownloadTask task) {
    Log.d(TAG, "下载失败");
  }

  @Download.onTaskRunning public void taskRunning(DownloadTask task) {
    Log.d(TAG, "PP = " + task.getPercent());
    setProgress(task.getPercent());
    setSpeed(task.getConvertSpeed());
  }

  @Download.onTaskComplete public void taskComplete(DownloadTask task) {
    setProgress(100);
    setSpeed("");
    T.showShort(this, "文件：" + task.getEntity().getFileName() + "，下载完成");
  }
}
