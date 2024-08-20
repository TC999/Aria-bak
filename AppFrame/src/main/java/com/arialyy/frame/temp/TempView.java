package com.arialyy.frame.temp;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lyy.frame.R;

/**
 * Created by lyy on 2016/1/11.
 * 错误填充View
 */
public class TempView extends AbsTempView {
  ProgressBar mPb;
  TextView mHint;
  Button mBt;
  FrameLayout mErrorContent;

  public TempView(Context context) {
    super(context);
  }

  @Override
  protected void init() {
    mPb = (ProgressBar) findViewById(R.id.pb);
    mHint = (TextView) findViewById(R.id.hint);
    mBt = (Button) findViewById(R.id.bt);
    mErrorContent = (FrameLayout) findViewById(R.id.error);
    mBt.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onTempBtClick(v, mType);
      }
    });
  }

  @Override
  protected int setLayoutId() {
    return R.layout.layout_error_temp;
  }

  @Override
  public void onError() {
    mErrorContent.setVisibility(VISIBLE);
    mPb.setVisibility(GONE);
    mHint.setText("网络错误");
    mBt.setText("点击刷新");
  }

  @Override
  public void onNull() {
    mErrorContent.setVisibility(VISIBLE);
    mPb.setVisibility(GONE);
    mHint.setText("数据为空");
    mBt.setText("点击刷新");
  }

  @Override
  public void onLoading() {
    mErrorContent.setVisibility(GONE);
    mPb.setVisibility(VISIBLE);
  }
}
