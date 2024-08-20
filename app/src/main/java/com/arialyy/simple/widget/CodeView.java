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

package com.arialyy.simple.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.arialyy.simple.R;
import com.arialyy.simple.core.FullScreenCodeActivity;
import com.pddstudio.highlightjs.HighlightJsView;
import com.pddstudio.highlightjs.models.Language;
import com.pddstudio.highlightjs.models.Theme;
import java.io.File;

/**
 * 代码高亮控件
 */
public class CodeView extends RelativeLayout {

  private HighlightJsView mCodeView;
  private File mSourceFile;

  public CodeView(Context context) {
    super(context, null);
  }

  public CodeView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  private void init(Context context) {
    LayoutInflater.from(context).inflate(R.layout.layout_code_demo, this, true);
    mCodeView = findViewById(R.id.js_view);
    mCodeView.setHighlightLanguage(Language.JAVA);
    mCodeView.setTheme(Theme.ANDROID_STUDIO);
    mCodeView.setZoomSupportEnabled(true);
    findViewById(R.id.full_screen).setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        // 横屏显示代码
        Intent intent = new Intent(getContext(), FullScreenCodeActivity.class);
        intent.putExtra(FullScreenCodeActivity.KEY_FILE_PATH, mSourceFile.getPath());
        getContext().startActivity(intent);
      }
    });
  }

  public void setSource(File sourceFile) {
    mSourceFile = sourceFile;
    mCodeView.setSource(sourceFile);
  }


  public void setSource(File sourceFile, Language language) {
    mSourceFile = sourceFile;
    mCodeView.setHighlightLanguage(language);
    mCodeView.setSource(sourceFile);
  }
}
