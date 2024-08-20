## 关于Aria，你还需要知道的一些东西
- 设置下载任务数，Aria默认下载任务为**2**

  ```java  
  Aria.get(getContext()).setMaxDownloadNum(num);
  ```
- 停止所有下载

  ```java
  Aria.get(this).stopAllTask();
  ```
- 设置失败重试次数，从事次数不能少于 1

  ```java
  Aria.get(this).setReTryNum(10);
  ```
- 设置失败重试间隔，重试间隔不能小于 5000ms

  ```java
  Aria.get(this).setReTryInterval(5000);
  ```
- 设置是否打开广播，如果你需要在Service后台获取下载完成情况，那么你需要打开Aria广播，[Aria广播配置](https://github.com/AriaLyy/Aria/blob/v_2.0/BroadCast.md)

  ```java
  Aria.get(this).openBroadcast(true);
  ```

## https证书配置
  + 将你的证书导入`assets`目录
  + 调用以下代码配置ca证书相关信息

  ```java
  /**
   * 设置CA证书信息
   *
   * @param caAlias ca证书别名
   * @param caPath assets 文件夹下的ca证书完整路径
   */
  Aria.get(this).setCAInfo("caAlias","caPath");
  ```
