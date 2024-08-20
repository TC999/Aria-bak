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

package com.arialyy.simple.core.download.mutil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.arialyy.simple.R;
import com.arialyy.simple.base.BaseDialog;
import com.arialyy.simple.databinding.DialogDownloadNumBinding;

/**
 * Created by “AriaLyy@outlook.com” on 2016/11/14.
 * 设置下载数量对话框
 */
@SuppressLint("ValidFragment") public class DownloadNumDialog
    extends BaseDialog<DialogDownloadNumBinding> implements RadioGroup.OnCheckedChangeListener {
  public static final int RESULT_CODE = 1001;
  Button mCancel;
  RadioGroup mRg;

  public DownloadNumDialog(Object obj) {
    super(obj);
  }

  @Override protected int setLayoutId() {
    return R.layout.dialog_download_num;
  }

  @Override protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    mCancel = findViewById(R.id.cancel);
    mRg = findViewById(R.id.rg);

    mCancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dismiss();
      }
    });
    for (int i = 0, count = mRg.getChildCount(); i < count; i++) {
      RadioButton rb = (RadioButton) mRg.getChildAt(i);
      rb.setId(i);
    }
    mRg.setOnCheckedChangeListener(this);
  }

  @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
    RadioButton rb = (RadioButton) group.getChildAt(checkedId);
    if (rb.isChecked()) {
      getSimplerModule().onDialog(RESULT_CODE, rb.getTag());
      dismiss();
    }
  }
}