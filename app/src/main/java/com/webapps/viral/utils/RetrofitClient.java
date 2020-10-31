package com.webapps.viral.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
         //   HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
           // logging.setLevel(HttpLoggingInterceptor.Level.BODY);

           // OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
             interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
             OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(500, TimeUnit.SECONDS)
                     .readTimeout(500,TimeUnit.SECONDS).writeTimeout(500,TimeUnit.SECONDS).build();
              // httpClient.addInterceptor(logging);  // <-- this is the important line!

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}