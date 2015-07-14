package ru.korsander.followgit.rest;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import ru.korsander.followgit.model.Follower;
import ru.korsander.followgit.model.User;

/**
 * Created by korsander on 12.07.2015.
 */
public interface GithubApi {
    @GET("/users/{user}/followers")
    void listFolowers(@Path("user") String user, @Query("page") int page, @Query("per_page") int limit, Callback<ArrayList<Follower>> callback);

    @GET("/users/{user}/followers")
    Response listFolowers(@Path("user") String user, @Query("page") int page, @Query("per_page") int limit);

    @GET("/users/{user}")
    User getUser(@Path("user") String user);
}
