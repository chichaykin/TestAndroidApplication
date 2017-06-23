package com.chichaykin.testandroidapplication;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by chichaykin on 23/06/2017.
 */

public class NetworkModule {
    private static final String TAG = "NetworkModule";
    private static final String URL = "https://www.dropbox.com";

    NetworkApi provides() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.interceptors().add(logging);
        }

//        File fileCache = new File(mContext.getCacheDir(), "cache");
//        fileCache.mkdir();
//        Log.d(TAG, "Cache: %s, exist %b", fileCache.getAbsolutePath(), fileCache.exists());
//        Cache cache = new Cache(fileCache, mCashSize);
//        clientBuilder.cache(cache);

        return new Retrofit.Builder().client(clientBuilder.build())
                .baseUrl(URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NetworkApi.class);
    }
}
