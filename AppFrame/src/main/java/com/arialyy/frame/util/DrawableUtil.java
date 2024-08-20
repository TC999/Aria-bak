package com.arialyy.frame.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DrawableUtil {

  /**
   * View 转换为bitmap
   */
  public static Bitmap convertViewToBitmap(View v) {
    v.setDrawingCacheEnabled(true);
    v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

    v.buildDrawingCache(true);
    Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
    v.setDrawingCacheEnabled(false); // clear drawing cache
    return b;
  }

  /**
   * 收缩图片
   */
  public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
    int width = drawable.getIntrinsicWidth();
    int height = drawable.getIntrinsicHeight();
    Bitmap oldbmp = drawableToBitmap(drawable);
    Matrix matrix = new Matrix();
    float scaleWidth = ((float) w / width);
    float scaleHeight = ((float) h / height);
    matrix.postScale(scaleWidth, scaleHeight);
    Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
        matrix, true);
    return new BitmapDrawable(null, newbmp);
  }

  /**
   * drawable转bitMap
   */
  public static Bitmap drawableToBitmap(Drawable drawable) {
    int width = drawable.getIntrinsicWidth();
    int height = drawable.getIntrinsicHeight();
    Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
        .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
        : Bitmap.Config.RGB_565);
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, width, height);
    drawable.draw(canvas);
    return bitmap;
  }

  /**
   * BitMap2Byte
   */
  public static byte[] getBitmapByte(Bitmap bitmap) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
    try {
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return out.toByteArray();
  }

  /**
   * Byte2BitMap
   */
  public static Bitmap getBitmapFromByte(byte[] temp) {
    if (temp != null) {
      Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
      return bitmap;
    } else {
      return null;
    }
  }
}
