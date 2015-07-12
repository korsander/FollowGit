package ru.korsander.followgit.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ru.korsander.followgit.R;
import ru.korsander.followgit.model.Follower;
import ru.korsander.followgit.rest.RestClient;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ClickItemCallback{
    private RecyclerView list;
    private LinearLayoutManager layoutManager;
    private FollowersAdapter adapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        list = (RecyclerView) view;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        adapter = new FollowersAdapter();
        adapter.setCallback(this);
        RestClient.getInstance().listFolowers("torvalds", "1", "100", new Callback<ArrayList<Follower>>() {
            @Override
            public void success(ArrayList<Follower> followers, Response response) {
//                String valueHeader = "";
//                List<Header> headers = response.getHeaders();
//                for(Header header : headers) {
//                    if(header.getName().equals("Link")) {
//                        valueHeader = header.getValue();
//                        break;
//                    }
//                }
                Log.e("FRAG", ">"+followers.size());
                adapter.addItems(followers);
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("FRAG", "error");
            }
        });
        return view;
    }

    @Override
    public void onRVItemClick(String url) {
        Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
    }
}
