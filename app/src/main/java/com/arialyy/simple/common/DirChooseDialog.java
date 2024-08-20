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
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.arialyy.simple.R;
import com.arialyy.simple.base.BaseDialog;
import com.arialyy.simple.base.adapter.AbsHolder;
import com.arialyy.simple.base.adapter.AbsRVAdapter;
import com.arialyy.simple.base.adapter.RvItemClickSupport;
import com.arialyy.simple.databinding.DialogChooseDirBinding;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AriaL on 2019/5/28.
 */
@SuppressLint("ValidFragment") public class DirChooseDialog
    extends BaseDialog<DialogChooseDirBinding> {
  public static final int DIR_CHOOSE_DIALOG_RESULT = 0xB2;
  private String mCurrentPath = Environment.getExternalStorageDirectory().getPath();
  private List<File> mData = new ArrayList<>();
  private DialogModule mModule;

  public DirChooseDialog(Object obj) {
    super(obj);
  }

  @Override protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    getBinding().list.setLayoutManager(new LinearLayoutManager(getContext()));
    getBinding().list.addItemDecoration(
        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    final Adapter adapter = new Adapter(getContext(), mData);
    getBinding().list.setAdapter(adapter);
    getBinding().setCurrentPath(mCurrentPath);

    mModule = ViewModelProviders.of(this).get(DialogModule.class);
    mModule.getDirs(mCurrentPath).observe(this, new Observer<List<File>>() {
      @Override public void onChanged(@Nullable List<File> files) {
        mData.clear();
        if (files != null && !files.isEmpty()) {
          mData.addAll(files);
        }
        adapter.notifyDataSetChanged();
        if (mCurrentPath.equals(Environment.getExternalStorageDirectory().getPath())) {
          getBinding().up.setVisibility(View.GONE);
        }
        getBinding().setCurrentPath(mCurrentPath);
        getBinding().currentPath.setSelected(true);
      }
    });

    getBinding().up.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        up();
      }
    });

    getBinding().enter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getSimplerModule().onDialog(DIR_CHOOSE_DIALOG_RESULT, mCurrentPath);
        dismiss();
      }
    });

    RvItemClickSupport.addTo(getBinding().list).setOnItemClickListener(
        new RvItemClickSupport.OnItemClickListener() {
          @Override public void onItemClicked(RecyclerView recyclerView, int position, View v) {
            getBinding().up.setVisibility(View.VISIBLE);
            mCurrentPath = mCurrentPath.concat("/").concat(mData.get(position).getName());
            mModule.getDirs(mCurrentPath);
          }
        });
  }

  private void up() {
    int endIndex = mCurrentPath.lastIndexOf("/");
    mCurrentPath = mCurrentPath.substring(0, endIndex);
    mModule.getDirs(mCurrentPath);
  }

  @Override public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      DisplayMetrics dm = new DisplayMetrics();
      getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
      dialog.getWindow()
          .setLayout((dm.widthPixels), ViewGroup.LayoutParams.WRAP_CONTENT);
      // 拦截返回键
      dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
        @Override public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
          if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mCurrentPath.equals(Environment.getExternalStorageDirectory().getPath())) {
              dismiss();
            } else {
              up();
            }
            return true;
          }
          return false;
        }
      });
    }
  }

  @Override protected int setLayoutId() {
    return R.layout.dialog_choose_dir;
  }

  /**
   * 适配器
   */
  private class Adapter extends AbsRVAdapter<File, Adapter.Holder> {

    Adapter(Context context, List<File> data) {
      super(context, data);
    }

    @Override protected Holder getViewHolder(View convertView, int viewType) {
      return new Holder(convertView);
    }

    @Override protected int setLayoutId(int type) {
      return R.layout.item_choose_dir;
    }

    @Override protected void bindData(Holder holder, int position, File item) {
      holder.text.setSelected(true);
      holder.text.setText(item.getName());
    }

    private class Holder extends AbsHolder {
      private TextView text;

      Holder(View itemView) {
        super(itemView);
        text = findViewById(R.id.text);
      }
    }
  }
}
