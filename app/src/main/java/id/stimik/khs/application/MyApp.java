package id.stimik.khs.application;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(logging)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                . writeTimeout(120, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);
        AndroidNetworking.enableLogging();
    }
}
