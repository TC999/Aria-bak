# 广播使用
#### 一、创建广播接收器，用来接收下载的各种状态
```java
private BroadcastReceiver mReceiver = new BroadcastReceiver() {
    long len = 0;
    @Override public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      //可以通过intent获取到下载实体，下载实体中包含了各种下载状态
      DownloadEntity entity = intent.getParcelableExtra(Aria.DOWNLOAD_ENTITY);
      switch (action) {
        case Aria.ACTION_PRE:  //预处理
          break;
        case Aria.ACTION_POST_PRE: //预处理完成
          //预处理完成，便可以获取文件的下载长度了
          len = entity.getFileSize();
          break;
        case Aria.ACTION_START:  //开始下载
          L.d(TAG, "download start");
          break;
        case Aria.ACTION_RESUME: //恢复下载
          L.d(TAG, "download resume");
          long location = intent.getLongExtra(DownloadManager.CURRENT_LOCATION, 1);
          break;
        case Aria.ACTION_RUNNING:  //下载中
          long current = intent.getLongExtra(DownloadManager.CURRENT_LOCATION, 0);
          break;
        case Aria.ACTION_STOP:   //停止下载
          L.d(TAG, "download stop");
          break;
        case Aria.ACTION_COMPLETE: //下载完成
          break;
        case Aria.ACTION_CANCEL:   //取消下载
          break;
        case Aria.ACTION_FAIL:     // 下载失败
          break;
      }
    }
  };
```

#### 二、在Activity中创建广播过滤器
```java
@Override protected void onResume() {
  super.onResume();
  IntentFilter filter = new IntentFilter();
  filter.addDataScheme(getPackageName());
  filter.addAction(Aria.ACTION_PRE);
  filter.addAction(Aria.ACTION_POST_PRE);
  filter.addAction(Aria.ACTION_RESUME);
  filter.addAction(Aria.ACTION_START);
  filter.addAction(Aria.ACTION_RUNNING);
  filter.addAction(Aria.ACTION_STOP);
  filter.addAction(Aria.ACTION_CANCEL);
  filter.addAction(Aria.ACTION_COMPLETE);
  filter.addAction(Aria.ACTION_FAIL);
  registerReceiver(mReceiver, filter);
}
```
