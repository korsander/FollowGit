package ru.korsander.followgit.model;

import java.util.ArrayList;

import retrofit.RetrofitError;

/**
 * Created by korsander on 14.07.2015.
 */
public class FollowersResponse {
    private ArrayList<Follower> items;
    private RetrofitError error;
    private int lastPage;

    public ArrayList<Follower> getItems() {
        return items;
    }

    public void setItems(ArrayList<Follower> items) {
        this.items = items;
    }

    public RetrofitError getError() {
        return error;
    }

    public void setError(RetrofitError error) {
        this.error = error;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }
}
