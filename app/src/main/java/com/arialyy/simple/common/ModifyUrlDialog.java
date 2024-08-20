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

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import com.arialyy.simple.R;
import com.arialyy.simple.base.BaseDialog;
import com.arialyy.simple.databinding.DialogModifyUrlBinding;

/**
 * Created by AriaL on 2019/5/27.
 */
@SuppressLint("ValidFragment") public class ModifyUrlDialog
    extends BaseDialog<DialogModifyUrlBinding> {
  public static final int MODIFY_URL_DIALOG_RESULT = 0xA1;

  private String mTitle, mText;

  public ModifyUrlDialog(Object obj, String title, String defaultText) {
    super(obj);
    mTitle = title;
    mText = defaultText;
  }

  @Override protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    getBinding().setTitle(mTitle);
    getBinding().setText(mText);
    getBinding().cancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dismiss();
      }
    });
    getBinding().enter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mText = getBinding().getText();
        getSimplerModule().onDialog(MODIFY_URL_DIALOG_RESULT, mText);
        dismiss();
      }
    });
    getBinding().edit.post(new Runnable() {
      @Override public void run() {
        getBinding().edit.setSelection(mText.length());
      }
    });
  }

  @Override protected int setLayoutId() {
    return R.layout.dialog_modify_url;
  }
}
