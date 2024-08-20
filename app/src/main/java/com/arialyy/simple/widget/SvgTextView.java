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
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import com.arialyy.simple.R;

public class SvgTextView extends RelativeLayout {

  private TextView textView;
  private AppCompatImageView icon;

  public SvgTextView(Context context) {
    this(context, null);
  }

  public SvgTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    LayoutInflater.from(getContext()).inflate(R.layout.layout_svg_text, this, true);
    textView = findViewById(R.id.text);
    icon = findViewById(R.id.image);

    // init values from custom attributes
    final TypedArray attributes =
        getContext().obtainStyledAttributes(attrs, R.styleable.SvgTextView);
    Drawable drawable = attributes.getDrawable(R.styleable.SvgTextView_svg_text_view_icon);
    if (drawable != null) {
      icon.setImageDrawable(drawable);
    }
    String str = attributes.getString(R.styleable.SvgTextView_text);
    if (!TextUtils.isEmpty(str)) {
      textView.setText(str);
    }

    attributes.recycle();
  }

  public void setIconClickListener(View.OnClickListener listener) {
    icon.setOnClickListener(listener);
  }

  @BindingAdapter(value = { "svg_text_view_icon" })
  public static void bindAttr(SvgTextView svgTextView, @DrawableRes int drawable) {
    svgTextView.icon.setImageResource(drawable);
  }

  public void setIcon(@DrawableRes int drawable) {
    icon.setImageResource(drawable);
  }

  public void setText(String text) {
    textView.setText(Html.fromHtml(text));
  }
}
