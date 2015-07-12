package ru.korsander.followgit.rest;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import ru.korsander.followgit.model.Follower;

/**
 * Created by korsander on 12.07.2015.
 */
public interface GithubApi {
    @GET("/users/{user}/followers")
    void listFolowers(@Path("user") String user, @Query("page") String page, @Query("per_page") String limit, Callback<ArrayList<Follower>> callback);
}
