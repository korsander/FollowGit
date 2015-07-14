package ru.korsander.followgit.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import ru.korsander.followgit.model.Follower;
import ru.korsander.followgit.model.FollowersResponse;
import ru.korsander.followgit.rest.RestClient;

/**
 * Created by korsander on 14.07.2015.
 */
public class FollowersLoader extends AsyncTaskLoader<FollowersResponse> {
    private static final String HEADER_LINK = "Link";
    private FollowersResponse currentData;
    private String login;
    private int page;
    private int limit;

    public FollowersLoader (Context context, String login, int page, int limit) {
        super(context);
        this.login = login;
        this.page = page;
        this.limit = limit;
    }

    @Override
    public FollowersResponse loadInBackground() {
        Gson gson = new GsonBuilder().create();
        FollowersResponse response = new FollowersResponse();
        try {
            Response r = RestClient.getInstance().listFolowers(login, page, limit);
            TypedInput body = r.getBody();
            if(body != null) {
                byte[] bodyBytes = ((TypedByteArray) body).getBytes();
                String bodyMime = body.mimeType();
                String bodyCharset = MimeUtil.parseCharset(bodyMime, "utf-8");
                String data = new String(bodyBytes, bodyCharset);
                response.setItems((ArrayList<Follower>) gson.fromJson(data, new TypeToken<ArrayList<Follower>>(){}.getType()));
            } else {
                response.setItems(new ArrayList<Follower>());
            }

            List<Header> headers = r.getHeaders();
            String valueHeader = "";
            for(Header header : headers) {
                if(header.getName().equals(HEADER_LINK)) {
                    valueHeader = header.getValue();
                    Pattern pattern = Pattern.compile("(\\d)+&");
                    Matcher matcher = pattern.matcher(valueHeader);
                    int maxPage = 0;
                    while (matcher.find()) {
                        String str = matcher.group();
                        int n = Integer.parseInt(str.substring(0, str.length()-1));
                        if(maxPage < n) maxPage = n;
                    }
                    response.setLastPage(maxPage);
                    break;
                }
            }
        } catch (RetrofitError e) {
            response.setError(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void deliverResult(FollowersResponse data) {
        if(isReset()) {
            releaseResources(data);
            return;
        }
        FollowersResponse oldData = currentData;
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
    public void onCanceled(FollowersResponse data) {
        super.onCanceled(data);
        releaseResources(data);
    }

    private void releaseResources(FollowersResponse data) {
        data = null;
    }
}
