package ru.korsander.followgit.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import retrofit.RetrofitError;
import ru.korsander.followgit.model.User;
import ru.korsander.followgit.rest.RestClient;

/**
 * Created by korsander on 13.07.2015.
 */
public class UserLoader extends AsyncTaskLoader<User> {
    private User currentData;
    private String login;

    public UserLoader (Context context, String login) {
        super(context);
        this.login = login;
    }

    @Override
    public User loadInBackground() {
        try {
            return RestClient.getInstance().getUser(login);
        } catch (RetrofitError e) {
            Log.e("USER_LOADER", "ErrOr" + e);
            return null;
        }
    }

    @Override
    public void deliverResult(User data) {
        if(isReset()) {
            releaseResources(data);
            return;
        }
        User oldData = currentData;
        currentData = data;
        if(isStarted()) {
            super.deliverResult(data);
        }

        if (oldData != null && oldData != data) {
            releaseResources(oldData);
        }
    }

    @Override
    protected void onStartLoading() {
        if (currentData != null) {
            deliverResult(currentData);
        }

        if (takeContentChanged() || currentData == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();

        if (currentData != null) {
            releaseResources(currentData);
        }
    }

    @Override
    public void onCanceled(User data) {
        super.onCanceled(data);
        releaseResources(data);
    }

    private void releaseResources(User data) {
        data = null;
    }
}
