
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.simple.R;
import com.arialyy.simple.base.BaseActivity;
import com.arialyy.simple.databinding.ActivityTestBinding;
import java.io.File;

/**
 * Created by lyy on 2019/5/28.
 * Ftp 下载
 * <a href="https://aria.laoyuyu.me/aria_doc/">文档</>
 */
public class FtpDownload extends BaseActivity<ActivityTestBinding> {
  String TAG = "TestFTPActivity";
  private final String URL = "ftp://192.168.1.3:21/download//AriaPrj.rar";
  private final String FILE_PATH = "/mnt/sdcard/";

  @Override protected int setLayoutId() {
    return R.layout.activity_test;
  }

  @Override protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    mBar.setVisibility(View.GONE);
    Aria.download(this).register();
  }

  @Download.onWait void onWait(DownloadTask task) {
    Log.d(TAG, "wait ==> " + task.getEntity().getFileName());
  }

  @Download.onPre protected void onPre(DownloadTask task) {
    Log.d(TAG, "onPre");
  }

  @Download.onTaskStart void taskStart(DownloadTask task) {
    Log.d(TAG, "onPreStart");
  }

  @Download.onTaskRunning protected void running(DownloadTask task) {
    Log.d(TAG, "running，speed=" + task.getConvertSpeed());
  }

  @Download.onTaskResume void taskResume(DownloadTask task) {
    Log.d(TAG, "resume");
  }

  @Download.onTaskStop void taskStop(DownloadTask task) {
    Log.d(TAG, "stop");
  }

  @Download.onTaskCancel void taskCancel(DownloadTask task) {
    Log.d(TAG, "cancel");
  }

  @Download.onTaskFail void taskFail(DownloadTask task) {
    Log.d(TAG, "fail");
  }

  @Download.onTaskComplete void taskComplete(DownloadTask task) {
    Log.d(TAG, "complete, md5 => " + CommonUtil.getFileMD5(new File(task.getKey())));
  }

  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.start:
        Aria.download(this)
            .loadFtp(URL)
            .setFilePath(FILE_PATH)
            .login("lao", "123456")
            //.asFtps() // ftps 配置
            //.setStorePath("/mnt/sdcard/Download/server.crt") //设置证书路径
            // .setAlias("www.laoyuyu.me") // 设置证书别名
            .start();
        break;
      case R.id.stop:
        Aria.download(this).loadFtp(FILE_PATH).stop();
        break;
      case R.id.cancel:
        Aria.download(this).loadFtp(FILE_PATH).cancel();
        break;
    }
  }
}
