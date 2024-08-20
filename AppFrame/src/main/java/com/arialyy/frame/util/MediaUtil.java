package com.arialyy.frame.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by Aria.Lao on 2018/1/4.
 * 多媒体工具
 */
public class MediaUtil {
  private MediaUtil() {
    throw new AssertionError();
  }

  /**
   * 获取音频、视频播放长度
   */
  public static long getDuration(String path) {
    MediaPlayer mediaPlayer = new MediaPlayer();
    try {
      mediaPlayer.setDataSource(path);
    } catch (IOException e) {
      e.printStackTrace();
      return -1;
    }
    int duration = mediaPlayer.getDuration();
    mediaPlayer.release();
    return duration;
  }

  /**
   * 格式化视频时间
   */
  public static String convertViewTime(long timeMs) {
    int totalSeconds = (int) (timeMs / 1000);

    int seconds = totalSeconds % 60;
    int minutes = (totalSeconds / 60) % 60;
    int hours = totalSeconds / 3600;
    StringBuilder sFormatBuilder = new StringBuilder();
    Formatter sFormatter = new Formatter(sFormatBuilder, Locale.getDefault());
    sFormatBuilder.setLength(0);
    if (hours > 0) {
      return sFormatter.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
    } else {
      return sFormatter.format("%02d:%02d", minutes, seconds).toString();
    }
  }

  /**
   * 获取音频封面
   */
  public static Bitmap getArtwork(Context context, String url) {
    Uri selectedAudio = Uri.parse(url);
    MediaMetadataRetriever myRetriever = new MediaMetadataRetriever();
    myRetriever.setDataSource(context, selectedAudio); // the URI of audio file
    byte[] artwork;
    artwork = myRetriever.getEmbeddedPicture();
    if (artwork != null) {
      return BitmapFactory.decodeByteArray(artwork, 0, artwork.length);
    }
    return null;
  }

  public static byte[] getArtworkAsByte(Context context, String url) {
    Uri selectedAudio = Uri.parse(url);
    MediaMetadataRetriever myRetriever = new MediaMetadataRetriever();
    myRetriever.setDataSource(context, selectedAudio); // the URI of audio file
    byte[] artwork;
    artwork = myRetriever.getEmbeddedPicture();
    if (artwork != null) {
      return artwork;
    }
    return null;
  }
}
