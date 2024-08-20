//package com.arialyy.frame.http;
//
//import java.util.concurrent.TimeUnit;
//
//import javax.inject.Singleton;
//
//import okhttp3.OkHttpClient;
//import dagger.Module;
//import dagger.Provides;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//@Module
//public class WebApiModule {
//    @Provides
//    @Singleton
//    public OkHttpClient provideOkHttpClient() {
//        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
////        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            builder.addInterceptor(logging);
////        }
//        builder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
//                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
//        return builder.build();
//    }
//
//    @Provides
//    @Singleton
//    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
//        Retrofit.Builder builder = new Retrofit.Builder();
//        builder.client(okHttpClient)
////                .baseUrl(GameConstant.PACKAGE_TEST_BASE_URL)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create());
//        return builder.build();
//    }
//}