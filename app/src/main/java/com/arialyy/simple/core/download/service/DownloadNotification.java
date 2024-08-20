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

package com.arialyy.simple.core.download.service;

import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import com.arialyy.simple.R;

/**
 * Created by lyy on 2017/1/18.
 */
public class DownloadNotification {

  private NotificationManager mManager;
  private Context mContext;
  private NotificationCompat.Builder mBuilder;
  private static final int mNotifiyId = 0;

  public DownloadNotification(Context context) {
    mContext = context;
    init();
  }

  private void init() {
    mManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    mBuilder = new NotificationCompat.Builder(mContext);
    mBuilder.setContentTitle("Aria Download Test")
        .setContentText("进度条")
        .setProgress(100, 0, false)
        .setSmallIcon(R.mipmap.ic_launcher);
    mManager.notify(mNotifiyId, mBuilder.build());
  }

  public void upload(int progress){
    if (mBuilder != null) {
      mBuilder.setProgress(100, progress, false);
      mManager.notify(mNotifiyId, mBuilder.build());
    }
  }
}
