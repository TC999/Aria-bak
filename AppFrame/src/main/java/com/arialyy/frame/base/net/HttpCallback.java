package com.arialyy.frame.base.net;

import com.arialyy.frame.util.show.FL;
import com.arialyy.frame.util.show.L;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by “Aria.Lao” on 2016/10/26.
 * HTTP数据回调
 */
public abstract class HttpCallback<T> implements INetResponse<T>, Observable.Transformer<T, T> {

  @Override public void onFailure(Throwable e) {
    L.e("HttpCallback", FL.getExceptionString(e));
  }

  @Override public Observable<T> call(Observable<T> observable) {
    Observable<T> tObservable = observable.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(new Func1<T, T>() {
          @Override public T call(T t) {
            onResponse(t);
            return t;
          }
        })
        .onErrorReturn(new Func1<Throwable, T>() {
          @Override public T call(Throwable throwable) {
            onFailure(throwable);
            return null;
          }
        });
    tObservable.subscribe();
    return tObservable;
  }
}
