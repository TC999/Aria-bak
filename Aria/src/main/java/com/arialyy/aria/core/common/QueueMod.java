package com.arialyy.aria.core.common;

/**
 * Created by Aria.Lao on 2017/6/21.
 * 执行队列类型
 */
public enum QueueMod {
  /**
   * 等待模式，
   * 如果执行队列已经满了，再对其它任务（TASK_A）使用start命令执行任务时
   * 1、TASK_A添加到缓存队列中，当执行队列中的任务完成时，系统会将自动执行缓存队列中的TASK_A
   * 2、如果再次对TASK_A使用start命令，TASK_A将会立刻执行
   */
  WAIT("wait"),

  /**
   * 立刻执行模式
   * 如果执行队列已经满了，再次使用start命令执行任务时，该任务会添加到执行队列队尾，而原来执行队列的队首任务会停止
   */
  NOW("now");

  public String tag;

  public String getTag() {
    return tag;
  }

  QueueMod(String tag) {
    this.tag = tag;
  }
}
