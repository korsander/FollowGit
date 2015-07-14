package ru.korsander.followgit.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.pnikosis.materialishprogress.ProgressWheel;

import ru.korsander.followgit.FollowGitApp;
import ru.korsander.followgit.R;
import ru.korsander.followgit.loader.FollowersLoader;
import ru.korsander.followgit.model.FollowersResponse;
import ru.korsander.followgit.utils.Const;


/**
 * A placeholder fragment containing a simple view.
 */
public class FollowersFragment extends Fragment implements ClickItemCallback, LoaderManager.LoaderCallbacks<FollowersResponse>{
    private ViewAnimator animator;
    private ProgressWheel bar;
    private RecyclerView list;
    private LinearLayoutManager layoutManager;
    private FollowersAdapter adapter;
    private static final String ARG_USERNAME = "arg_username";
    private static String username;
    private static final String USER_DIALOG_TAG = "user_dialog";

    public static final String FRAGMENT_NAME = "followers";

    private static final String ARG_LOGIN = "login";
    private static final String ARG_PAGE = "page";
    private static final String ARG_LIMIT = "limit";
    private static final int LOADER_FOLLOWERS = 1;

    public static FollowersFragment newInstance(String username) {
        FollowersFragment fragment = new FollowersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    public FollowersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
        }
        Bundle args = new Bundle();
        args.putString(ARG_LOGIN, username);
        args.putInt(ARG_PAGE, 1);
        args.putInt(ARG_LIMIT, Const.LIMIT);
        getLoaderManager().initLoader(LOADER_FOLLOWERS, args, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        animator = (ViewAnimator) view;
        list = (RecyclerView) view.findViewById(R.id.list);
        bar = (ProgressWheel) view.findViewById(R.id.progress_bar);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.addOnScrollListener(new RecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Bundle args = new Bundle();
                args.putString(ARG_LOGIN, username);
                args.putInt(ARG_PAGE, current_page);
                args.putInt(ARG_LIMIT, Const.LIMIT);
                getLoaderManager().restartLoader(LOADER_FOLLOWERS, args, FollowersFragment.this);
            }
        });
        adapter = new FollowersAdapter();
        adapter.setCallback(this);

        return view;
    }

    @Override
    public void onRVItemClick(String login) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(USER_DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        UserDialog ud = UserDialog.newInstance(login);
        ud.show(ft, USER_DIALOG_TAG);
    }

    @Override
    public Loader<FollowersResponse> onCreateLoader(int id, Bundle args) {
        Loader<FollowersResponse> loader = null;
        Log.e(">", "load start");
        if(id == LOADER_FOLLOWERS && args.containsKey(ARG_LOGIN) && args.containsKey(ARG_PAGE) && args.containsKey(ARG_LIMIT)) {
            loader = new FollowersLoader(FollowGitApp.getContext(), args.getString(ARG_LOGIN), args.getInt(ARG_PAGE), args.getInt(ARG_LIMIT));
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<FollowersResponse> loader, FollowersResponse data) {
        if(loader.getId() == LOADER_FOLLOWERS) {
            Log.e(">", "load finish");
            if(data.getError() != null) {
                Toast.makeText(FollowGitApp.getContext(), data.getError().getMessage(), Toast.LENGTH_LONG).show();
            } else if(data.getItems().size() == 0) {
                Toast.makeText(FollowGitApp.getContext(), String.format(FollowGitApp.getContext().getString(R.string.user_0_followers), username) ,Toast.LENGTH_SHORT).show();
            } else {
                adapter.addItems(data.getItems());
                adapter.setLastPage(data.getLastPage());
                if(list.getAdapter() == null) list.setAdapter(adapter);
            }
            if(animator.getCurrentView().getId() == R.id.progress_bar) {
                animator.showNext();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<FollowersResponse> loader) {
    }
}
