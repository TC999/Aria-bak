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

package com.arialyy.simple.core;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import com.arialyy.aria.util.ALog;
import com.arialyy.simple.R;
import com.arialyy.simple.base.BaseActivity;
import com.arialyy.simple.databinding.ActivityFullScreenCodeBinding;
import com.pddstudio.highlightjs.models.Language;
import com.pddstudio.highlightjs.models.Theme;
import java.io.File;

/**
 * 全是显示代码的actiivty
 */
public class FullScreenCodeActivity extends BaseActivity<ActivityFullScreenCodeBinding> {
  private static final String TAG = "FullScreenCodeActivity";
  public static final String KEY_FILE_PATH = "KEY_FILE_PATH";

  @Override protected int setLayoutId() {
    return R.layout.activity_full_screen_code;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    //去除标题栏
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    //去除状态栏
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
  }

  @Override protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    String filePath = getIntent().getStringExtra(KEY_FILE_PATH);

    if (TextUtils.isEmpty(filePath)) {
      ALog.e(TAG, "代码的文件路径为空");
      finish();
      return;
    }
    getBinding().codeView.setZoomSupportEnabled(true);
    getBinding().codeView.setHighlightLanguage(Language.JAVA);
    getBinding().codeView.setTheme(Theme.ANDROID_STUDIO);
    getBinding().codeView.setSource(new File(filePath));

    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
  }
}
