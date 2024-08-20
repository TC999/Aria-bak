package com.arialyy.frame.util;

import com.arialyy.frame.util.show.FL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日历工具类 Create by lyy on 13-7-8.
 */
public class CalendarUtils {
  private static final String TAG = "CalendarUtils";
  /**
   * 完整的日期时间格式
   */
  public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  /**
   * 只有日期的格式
   */
  public final static String DATE_FORMAT = "yyyy-MM-dd";
  /**
   * 只有时间的格式
   */
  public final static String TIME_FORMAT = "HH:mm:ss";
  /**
   * 带中文的日期格式(2000年01月01日)
   */
  public final static String DATE_FORMAT_WITH_CHINESE = "yyyy年MM月dd日";
  /**
   * 短时间格式(HH:mm)
   */
  public final static String SHORT_TIME_FORMAT = "HH:mm";

  /**
   * 私有构造
   */
  private CalendarUtils() {
  }

  /**
   * 把String类型的日期转换成Calendar对象
   *
   * @param string （日期时间:2000-00-00 00:00:00)
   */
  public static Calendar transformStringToCalendar(String string) {
    return transformStringToCalendar(string, DATE_TIME_FORMAT);
  }

  /**
   * 通过SimpleDataFormat格式把string转换成Calendar
   *
   * @param string 日期字符串
   * @param format 目标日期格式
   */
  public static Calendar transformStringToCalendar(String string, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date date = null;
    try {
      date = sdf.parse(string);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }

  /**
   * 把日期字符串转换成TimeMillis
   */
  public static long transformStringToMillis(String string) {
    return transformStringToCalendar(string).getTimeInMillis();
  }

  /**
   * 通过TimeMillis转换成秒钟
   *
   * @param millis TimeMillis
   */
  public static long getSecondWithTimeMillis(long millis) {
    return millis / 1000;
  }

  /**
   * 返回两个日期相差的秒
   */
  public static long getIntervalInSeconds(Calendar calendar, Calendar targetCalendar) {
    return (calendar.getTimeInMillis() - targetCalendar.getTimeInMillis()) / 1000;
  }

  /**
   * 格式化日期
   *
   * @param string 有效的日期字符
   * @param format 格式化的格式
   */
  public static String formatWithString(String string, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(transformStringToCalendar(string).getTime());
  }

  /**
   * 格式化日期
   *
   * @param src 源日期字符
   * @param srcFormat 源日期格式
   * @param targetFormat 目标日期格式
   */
  public static String formatWithString(String src, String srcFormat, String targetFormat) {
    SimpleDateFormat sdf = new SimpleDateFormat(srcFormat);
    try {
      Date date = sdf.parse(src);
      SimpleDateFormat targetSdf = new SimpleDateFormat(targetFormat);
      return targetSdf.format(date);
    } catch (Exception e) {
      FL.e(TAG, "src=" + src + "  " + srcFormat + "  " + targetFormat);
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 格式化成日期时间
   *
   * @param string 将要被格式化的日期字符串
   * @return 格式:(2000-00-00 00:00:00)
   */
  public static String formatDateTimeWithString(String string) {
    return formatWithString(string, DATE_TIME_FORMAT);
  }

  /**
   * 格式化日期
   *
   * @param srcDate 源日期字符
   * @param srcDateFormat 源日期格式
   * @param targetFormat 目标格式
   */
  public static String formatDateTimeWithString(String srcDate, String srcDateFormat,
      String targetFormat) {
    SimpleDateFormat sdf = new SimpleDateFormat(srcDateFormat);
    try {
      Date date = sdf.parse(srcDate);
      SimpleDateFormat parseSdf = new SimpleDateFormat(targetFormat);
      return parseSdf.format(date);
    } catch (ParseException e) {
      FL.e(TAG, "srcDate:"
          + srcDate
          + "  srcDateFormat:"
          + srcDateFormat
          + "   targetFormat"
          + targetFormat);
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 格式化日期
   *
   * @param string 将要被格式化的日期字符串
   * @return 格式:(2000-00-00)
   */
  public static String formatDateWithString(String string) {
    return formatWithString(string, DATE_FORMAT);
  }

  /**
   * 格式化日期
   *
   * @param string 将要被格式化的日期字符串
   * @return 格式:(00:00:00)
   */
  public static String formatTimeWithString(String string) {
    return formatWithString(string, TIME_FORMAT);
  }

  /**
   * 格式化日期
   *
   * @param string 将要被格式化的日期字符串
   * @return 格式:(2000年01月01日)
   */
  public static String formatStringToChinese(String string) {
    return formatWithString(string, DATE_FORMAT_WITH_CHINESE);
  }

  /**
   * Data日期格式化成String
   *
   * @param date 将要被格式化的data
   * @return 格式:(2000-00-00 00:00:00)
   */
  public static String formatDateTimeWithDate(Date date) {
    return formatStringWithDate(date, DATE_TIME_FORMAT);
  }

  /**
   * 时间戳格式的数据格式化成需要的格式
   *
   * @param string 将要被格式化的时间戳
   * @return 格式:(2000-00-00 00:00:00)
   */
  public static String formatDateTimeWithTime(String string) {
    return formatStringWithDate(timeToData(string), DATE_TIME_FORMAT);
  }

  /**
   * 把中文日期（2000年01月01日)格式化成标准日期(2000-01-01)
   *
   * @param data 将要格式化的日期字符串
   * @return 格式:(2000-00-00);
   */
  public static String formatChineseDataToData(String data) {
    data = data.replace("年", "-");
    data = data.replace("月", "-");
    data = data.replace("日", "");
    return data;
  }

  /**
   * 日期格式化成String
   *
   * @param date 将要格式化的日期字符串
   * @return 格式：（format格式）
   */
  public static String formatStringWithDate(Date date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }

  /**
   * 把String类型的时间转换成Calendar
   *
   * @param time 时间格式：00:00:00
   */
  public static Calendar transformStringTimeToCalendar(String time) {
    Calendar calendar = Calendar.getInstance();
    String[] split = time.split(":");
    if (split.length > 0) {
      calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split[0]));
    }
    if (split.length > 1) {
      calendar.set(Calendar.MINUTE, Integer.parseInt(split[1]));
    }
    if (split.length > 2) {
      calendar.set(Calendar.SECOND, Integer.parseInt(split[2]));
    }
    return calendar;
  }

  /**
   * 把时间戳:1234567890123,转换成Date对象
   */
  public static Date timeToData(String time) {
    return new Date(Long.parseLong(time));
  }

  /**
   * 比较两个字符串日期的大小
   *
   * @return 小于0：srcData 小于 tagData;
   * 等于0：则srcData = tagData;
   * 大于0：则srcData 大余 tagData
   */
  public static int compare(String srcDate, String tagDate) {
    return srcDate.compareTo(tagDate);
  }

  /**
   * 返回现在的日期和时间
   *
   * @return 格式:2000-00-00 00:00:00
   */
  public static String getNowDataTime() {
    Calendar calendar = Calendar.getInstance();
    return formatDateTimeWithDate(calendar.getTime());
  }

  /**
   * 返回当前的日期
   *
   * @return 格式：2000-00-00
   */
  public static String getData() {
    return getNowDataTime().split(" ")[0];
  }

  /**
   * 返回当前时间
   *
   * @return 格式：00:00:00
   */
  public static String getTime() {
    return getNowDataTime().split(" ")[1];
  }
}
