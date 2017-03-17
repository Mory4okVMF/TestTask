package com.kartuzov.testappgit.gittest;

import android.app.Application;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class App extends Application {

    private static SearchClientService client;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        client = retrofit.create(SearchClientService.class);
    }

    public static SearchClientService getApi() {
        return client;
    }
}