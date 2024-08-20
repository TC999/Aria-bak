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
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.arialyy.simple.R;
import com.arialyy.simple.base.BaseDialog;
import com.arialyy.simple.databinding.DialogModifyPathBinding;
import com.arialyy.simple.databinding.DialogMsgBinding;
import java.io.File;

/**
 * Created by AriaL on 2019/5/28.
 * 修改文件路径
 */
@SuppressLint("ValidFragment") public class ModifyPathDialog
    extends BaseDialog<DialogModifyPathBinding> {
  public static final int MODIFY_PATH_RESULT = 0xB3;

  private String mTitle, mFilePath, mDir;

  public ModifyPathDialog(Object obj, String title, String filePath) {
    super(obj);
    mTitle = title;
    mFilePath = filePath;
  }

  @Override protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    getBinding().setTitle(mTitle);
    getBinding().setViewModel(this);
    final File temp = new File(mFilePath);
    mDir = temp.getParent();
    getBinding().setDir(mDir);
    getBinding().setName(temp.getName());
    getBinding().edit.post(new Runnable() {
      @Override public void run() {
        getBinding().edit.setSelection(temp.getName().length());
      }
    });
    getBinding().enter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (TextUtils.isEmpty(getBinding().getName())) {
          Toast.makeText(getContext(), getString(R.string.error_file_name_null), Toast.LENGTH_SHORT)
              .show();
          return;
        }
        mFilePath = mDir + "/" + getBinding().getName();
        getSimplerModule().onDialog(MODIFY_PATH_RESULT, mFilePath);
        dismiss();
      }
    });
    getBinding().cancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dismiss();
      }
    });
  }

  public void chooseDir() {
    DirChooseDialog dirChooseDialog = new DirChooseDialog(this);
    dirChooseDialog.show(getChildFragmentManager(), "DirChooseDialog");
  }

  @Override protected int setLayoutId() {
    return R.layout.dialog_modify_path;
  }

  @Override protected void dataCallback(int result, Object data) {
    super.dataCallback(result, data);
    if (result == DirChooseDialog.DIR_CHOOSE_DIALOG_RESULT) {
      mDir = String.valueOf(data);
      getBinding().setDir(mDir);
    }
  }
}
