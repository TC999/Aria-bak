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

package com.arialyy.simple.base;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;
import com.arialyy.frame.core.AbsActivity;
import com.arialyy.frame.util.AndroidVersionUtil;
import com.arialyy.simple.R;
import com.arialyy.simple.common.MsgDialog;

/**
 * Created by Lyy on 2016/9/27.
 */
public abstract class BaseActivity<VB extends ViewDataBinding> extends AbsActivity<VB>
    implements Toolbar.OnMenuItemClickListener {

  protected Toolbar mBar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (AndroidVersionUtil.hasLollipop()) {
      getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }
  }

  @Override protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    mBar = findViewById(R.id.toolbar);
    if (mBar != null) {
      setSupportActionBar(mBar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      mBar.setOnMenuItemClickListener(this);
    }
  }

  protected void setTile(String title) {
    if (mBar == null) {
      mBar = findViewById(R.id.toolbar);
    }
    mBar.setTitle(title);
  }

  protected void showMsgDialog(String title, String msg) {
    MsgDialog dialog = new MsgDialog(this, title, msg);
    dialog.show(getSupportFragmentManager(), "msg_dialog");
  }

  @Override public boolean onMenuItemClick(MenuItem item) {

    return false;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void dataCallback(int result, Object data) {

  }
}