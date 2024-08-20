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
package com.arialyy.simple.base.adapter;

import android.util.SparseArray;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lyy on 2015/12/3.
 * 通用Holder
 */
public class AbsHolder extends RecyclerView.ViewHolder {
  private View mView;
  private SparseArray<View> mViews = new SparseArray<>();

  public AbsHolder(View itemView) {
    super(itemView);
    mView = itemView;
  }

  @SuppressWarnings("unchecked")
  public <T extends View> T findViewById(@IdRes int id) {
    View view = mViews.get(id);
    if (view == null) {
      view = mView.findViewById(id);
      mViews.put(id, view);
    }
    return (T) view;
  }
}