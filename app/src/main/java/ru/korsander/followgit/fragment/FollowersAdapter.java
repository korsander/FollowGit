package ru.korsander.followgit.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.korsander.followgit.FollowGitApp;
import ru.korsander.followgit.R;
import ru.korsander.followgit.model.Follower;

/**
 * Created by korsander on 12.07.2015.
 */
public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {
    private ArrayList<Follower> items;
    private ClickItemCallback callback;
    private int lastPosition = -1;
    private int lastPage = -1;

    public FollowersAdapter() {
        items = new ArrayList<Follower>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view, new ViewHolder.OnVHClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                callback.onRVItemClick(items.get(position).getLogin());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Follower follower = items.get(position);
        holder.tvLogin.setText(follower.getLogin());
        Picasso.with(FollowGitApp.getContext()).load(follower.getAvatar()).into(holder.ivAvatar);
        Animation animation = AnimationUtils.loadAnimation(FollowGitApp.getContext(),
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public ClickItemCallback getCallback() {
        return callback;
    }

    public void setCallback(ClickItemCallback callback) {
        this.callback = callback;
    }

    public ArrayList<Follower> getItems() {
        return items;
    }

    public void setItems(ArrayList<Follower> items) {
        this.items = items;
    }

    public void addItems(ArrayList<Follower> items) {
        this.items.addAll(items);
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivAvatar;
        private TextView tvLogin;
        private OnVHClickListener listener;

        public ViewHolder(View view, OnVHClickListener listener) {
            super(view);
            ivAvatar = (ImageView) view.findViewById(R.id.ivAvatarList);
            tvLogin = (TextView) view.findViewById(R.id.tvLoginList);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }

        public interface OnVHClickListener {
            void onItemClick(View view, int position);
        }
    }
}
