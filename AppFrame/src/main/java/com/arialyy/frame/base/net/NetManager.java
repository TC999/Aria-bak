package com.arialyy.frame.base.net;

import android.util.SparseArray;
import com.arialyy.frame.base.BaseApp;
import com.arialyy.frame.config.CommonConstant;
import com.arialyy.frame.config.NetConstant;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by “Aria.Lao” on 2016/10/25.
 * 网络管理器
 */
public class NetManager {
  private static final Object LOCK = new Object();
  private static volatile NetManager INSTANCE = null;
  private static final long TIME_OUT = 8 * 1000;
  private Retrofit mRetrofit;
  private Retrofit.Builder mBuilder;
  private SparseArray<GsonConverterFactory> mConverterFactorys = new SparseArray<>();
  private ClearableCookieJar mCookieJar;

  private NetManager() {
    init();
  }

  public static NetManager getInstance() {
    if (INSTANCE == null) {
      synchronized (LOCK) {
        INSTANCE = new NetManager();
      }
    }
    return INSTANCE;
  }

  OkHttpClient okHttpClient;

  private void init() {
    mCookieJar = new PersistentCookieJar(new SetCookieCache(),
        new SharedPrefsCookiePersistor(BaseApp.context));
    //OkHttpClient okHttpClient = provideOkHttpClient();
    okHttpClient = provideOkHttpClient();
  }

  public ClearableCookieJar getCookieJar() {
    return mCookieJar;
  }

  /**
   * 执行网络请求
   *
   * @param service 服务器返回的实体类型
   * @param gson gson 为传入的数据解析器，ENTITY 为 网络实体
   * <pre><code>
   *   Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<ENTITY>() {
   *      }.getType(), new BasicDeserializer<ENTITY>()).create();
   *
   *   //如启动图，需要将‘ENTITY’替换为启动图实体‘LauncherImgEntity’
   *   Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<LauncherImgEntity>() {
   *   }.getType(), new BasicDeserializer<LauncherImgEntity>()).create();
   *
   * </code></pre>
   */
  public <SERVICE> SERVICE request(Class<SERVICE> service, Gson gson) {
    GsonConverterFactory f = null;
    if (gson == null) {
      f = GsonConverterFactory.create();
    } else {
      f = GsonConverterFactory.create(gson);
    }
    ;
    final Retrofit.Builder builder = new Retrofit.Builder().client(okHttpClient)
        .baseUrl(NetConstant.BASE_URL)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    builder.addConverterFactory(f);
    return builder.build().create(service);
  }

  /**
   * 创建OKHTTP
   */
  private OkHttpClient provideOkHttpClient() {
    final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    if (CommonConstant.DEBUG) {
      //HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      //logging.setLevel(HttpLoggingInterceptor.Level.BODY);
      //builder.addInterceptor(logging);
      builder.addInterceptor(new OkHttpLogger());
    }
    builder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
    builder.cookieJar(mCookieJar);
    //builder.addInterceptor(chain -> {
    //  //String cookies = CookieUtil.getCookies();
    //  Request request = chain.request().newBuilder()
    //      //.addHeader("Content-Type", "application/x-www-form-urlencoded")
    //      //.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
    //      //.addHeader("Cookie", cookies)
    //      .build();
    //  return chain.proceed(request);
    //});
    return builder.build();
  }
}
