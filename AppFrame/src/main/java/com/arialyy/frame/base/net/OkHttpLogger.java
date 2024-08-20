package com.arialyy.frame.base.net;

import com.arialyy.frame.util.show.FL;
import com.arialyy.frame.util.show.L;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Lyy on 2016/9/19.
 * 自定义的 OKHTTP 日志
 */
public class OkHttpLogger implements Interceptor {
  final static String TAG = "OKHTTP";

  @Override public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    long startNs = System.nanoTime();
    Response response = chain.proceed(request);
    long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
    ResponseBody responseBody = response.body();
    long contentLength = responseBody.contentLength();
    String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
    L.d(TAG, "<-- "
        + response.code()
        + ' '
        + response.message()
        + ' '
        + response.request().url()
        + " ("
        + tookMs
        + "ms"
        + (", " + bodySize + " body")
        + ')');
    //Headers headers = response.headers();
    //for (int i = 0, count = headers.size(); i < count; i++) {
    //  FL.d(TAG, headers.name(i) + ": " + headers.value(i));
    //}
    BufferedSource source = responseBody.source();
    source.request(Long.MAX_VALUE); // Buffer the entire body.
    Buffer buffer = source.buffer();
    Charset UTF8 = Charset.forName("UTF-8");
    Charset charset = UTF8;
    MediaType contentType = responseBody.contentType();
    if (contentType != null) {
      charset = contentType.charset(UTF8);
    }
    if (contentLength != 0) {
      //FL.j(TAG, buffer.clone().readString(charset));
      L.j(buffer.clone().readString(charset));
    }

    L.d(TAG, "<-- END HTTP (" + buffer.size() + "-byte body)");

    return response;
  }
}
