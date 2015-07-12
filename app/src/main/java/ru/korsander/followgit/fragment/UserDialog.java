package ru.korsander.followgit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ru.korsander.followgit.R;

/**
 * Created by korsander on 12.07.2015.
 */
public class UserDialog extends DialogFragment implements View.OnClickListener {
    private static final String ARG_URL = "arg_url";
    private static String url;
    private ImageView ivAvatar;
    private TextView tvLogin,
            tvRepos,
            tvCreated;
    private Button button;

    public UserDialog() {
    }

    public static UserDialog newInstance(int state) {
        UserDialog dialog = new UserDialog();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            url = getArguments().getString(ARG_URL);
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Base_Theme_AppCompat_Light_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAvatarDialog);
        tvLogin = (TextView) view.findViewById(R.id.tvLoginDialog);
        tvRepos = (TextView) view.findViewById(R.id.tvReposDialog);
        tvCreated = (TextView) view.findViewById(R.id.tvCreatedDialog);
        button = (Button) view.findViewById(R.id.btnView);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnView) {

        }
    }
}
