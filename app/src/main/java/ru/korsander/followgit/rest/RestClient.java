package ru.korsander.followgit.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import ru.korsander.followgit.utils.Const;

/**
 * Created by korsander on 12.07.2015.
 */
public class RestClient {
    private static GithubApi api;

    static {
        initRestClient();
    }

    public static GithubApi getInstance() {
        return api;
    }

    private RestClient() {
    }

    private static void initRestClient() {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        client.setConnectTimeout(Const.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        client.setReadTimeout(Const.READ_TIMEOUT, TimeUnit.SECONDS);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(Const.API_PATH)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(client)).setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        api = adapter.create(GithubApi.class);
    }
}
