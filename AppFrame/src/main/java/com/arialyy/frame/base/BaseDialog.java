package com.arialyy.frame.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import androidx.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import com.arialyy.frame.core.AbsDialogFragment;
import com.arialyy.frame.util.AndroidUtils;

/**
 * Created by Aria.Lao on 2017/12/4.
 */

public abstract class BaseDialog<VB extends ViewDataBinding> extends AbsDialogFragment<VB> {
  private WindowManager.LayoutParams mWpm;
  private Window mWindow;
  protected boolean useDefaultAnim = true;

  @Override protected void init(Bundle savedInstanceState) {
    mWindow = getDialog().getWindow();
    if (mWindow != null) {
      mWpm = mWindow.getAttributes();
    }
    if (mWpm != null && mWindow != null) {
      //mView = mWindow.getDecorView();
      mRootView.setBackgroundColor(Color.WHITE);
      mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      //in();
      if (useDefaultAnim) {
        in1();
      }
    }
  }

  @Override public void dismiss() {
    if (mWpm != null && mWindow != null) {
      if (useDefaultAnim) {
        out();
      }
    } else {
      super.dismiss();
    }
  }

  @Override protected void dataCallback(int result, Object data) {

  }

  /**
   * 进场动画
   */
  private void in() {
    int height = AndroidUtils.getScreenParams(getContext())[1];
    ValueAnimator animator = ValueAnimator.ofObject(new IntEvaluator(), -height / 2, 0);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        mWpm.y = (int) animation.getAnimatedValue();
        mWindow.setAttributes(mWpm);
      }
    });
    animator.setInterpolator(new BounceInterpolator()); //弹跳
    Animator alpha = ObjectAnimator.ofFloat(mRootView, "alpha", 0f, 1f);
    AnimatorSet set = new AnimatorSet();
    set.play(animator).with(alpha);
    set.setDuration(2000).start();
  }

  private void in1() {
    Animator alpha = ObjectAnimator.ofFloat(mRootView, "alpha", 0f, 1f);
    alpha.setDuration(800);
    alpha.start();
  }

  /**
   * 重力动画
   */
  private void out() {
    int height = AndroidUtils.getScreenParams(getContext())[1];
    ValueAnimator animator = ValueAnimator.ofObject(new IntEvaluator(), 0, height / 3);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        mWpm.y = (int) animation.getAnimatedValue();
        mWindow.setAttributes(mWpm);
      }
    });
    Animator alpha = ObjectAnimator.ofFloat(mRootView, "alpha", 1f, 0f);
    AnimatorSet set = new AnimatorSet();
    set.play(animator).with(alpha);
    set.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        BaseDialog.super.dismiss();
      }
    });
    set.setDuration(600).start();
  }
}
