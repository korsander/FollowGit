package ru.korsander.followgit.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;

import ru.korsander.followgit.FollowGitApp;
import ru.korsander.followgit.R;
import ru.korsander.followgit.loader.UserLoader;
import ru.korsander.followgit.model.User;
import ru.korsander.followgit.utils.Const;

/**
 * Created by korsander on 12.07.2015.
 */
public class UserDialog extends DialogFragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<User> {
    private static final String ARG_URL = "arg_url";
    private static String login;
    private ImageView ivAvatar;
    private TextView tvLogin,
            tvRepos,
            tvCreated;
    private Button button;
    private ViewAnimator animator;
    private ProgressWheel bar;

    private static final int LOADER_USER = 2;

    public UserDialog() {
    }

    public static UserDialog newInstance(String login) {
        UserDialog dialog = new UserDialog();
        Bundle args = new Bundle();
        args.putString(ARG_URL, login);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            login = getArguments().getString(ARG_URL);
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Base_Theme_AppCompat_Light_Dialog);
        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(LOADER_USER, bundle, this);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null  && FollowGitApp.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_user, container, false);
        animator = (ViewAnimator) view;
        bar = (ProgressWheel) view.findViewById(R.id.progress_bar);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAvatarDialog);
        tvLogin = (TextView) view.findViewById(R.id.tvLoginDialog);
        tvRepos = (TextView) view.findViewById(R.id.tvReposDialog);
        tvCreated = (TextView) view.findViewById(R.id.tvCreatedDialog);
        button = (Button) view.findViewById(R.id.btnView);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnView) {
            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Const.GITHUB_PATH + login)));
        }
    }

    @Override
    public Loader<User> onCreateLoader(int i, Bundle bundle) {
        Loader<User> loader = null;
        Log.e(">", "load start");
        if(i == LOADER_USER) {
            loader = new UserLoader(FollowGitApp.getContext(), login);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<User> loader, User user) {
        if(loader.getId() == LOADER_USER) {
            Log.e(">", "load finish");
            if(user != null) {
                Picasso.with(FollowGitApp.getContext()).load(user.getAvatar()).into(ivAvatar);
                button.setOnClickListener(this);
                tvLogin.setText(user.getLogin());
                tvRepos.setText(FollowGitApp.getContext().getString(R.string.dialog_repos) + " " + user.getRepos());
                tvCreated.setText(FollowGitApp.getContext().getString(R.string.dialog_created) + " " + user.getCreated());

                if(animator.getCurrentView().getId() == R.id.progress_bar) {
                    animator.showNext();
                }
            } else {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if(UserDialog.this != null) {
                            UserDialog.this.dismiss();
                        }
                    }
                });

            }
        }
    }

    @Override
    public void onLoaderReset(Loader<User> loader) {
    }
}
