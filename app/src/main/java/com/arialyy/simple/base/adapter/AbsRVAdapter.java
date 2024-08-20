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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyy on 2015/12/3.
 * RecyclerView 通用Adapter
 */
public abstract class AbsRVAdapter<T, Holder extends AbsHolder>
    extends RecyclerView.Adapter<Holder> {
  protected String TAG;
  protected List<T> mData = new ArrayList<>();
  protected Context mContext;
  Holder holder;

  public AbsRVAdapter(Context context, List<T> data) {
    mData = data;
    mContext = context;
    String arrays[] = getClass().getName().split("\\.");
    TAG = arrays[arrays.length - 1];
  }

  @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(setLayoutId(viewType), parent, false);
    //LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    //VD binding = DataBindingUtil.inflate(inflater, setLayoutId(viewType), parent, false);
    //;
    holder = getViewHolder(view, viewType);
    return holder;
  }

  protected abstract Holder getViewHolder(View convertView, int viewType);

  @Override public void onBindViewHolder(Holder holder, int position) {
    bindData(holder, position, mData.get(position));
  }

  @Override public void onBindViewHolder(Holder holder, int position, List<Object> payloads) {
    if (payloads == null || payloads.isEmpty()) {
      bindData(holder, position, mData.get(position));
    } else {
      bindData(holder, position, mData.get(position), payloads);
    }
  }

  public Context getContext() {
    return mContext;
  }

  @Override public int getItemCount() {
    return mData.size();
  }

  /**
   * item 的type
   */
  protected abstract int setLayoutId(int type);

  protected abstract void bindData(Holder holder, int position, T item);

  protected void bindData(Holder holder, int position, T item, List<Object> payloads) {

  }
}