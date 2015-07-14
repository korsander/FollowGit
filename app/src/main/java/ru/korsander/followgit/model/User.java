package ru.korsander.followgit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.korsander.followgit.utils.Const;
import ru.korsander.followgit.utils.Utils;

/**
 * Created by korsander on 13.07.2015.
 */
public class User {
    @Expose
    private String login;
    @SerializedName("avatar_url")
    @Expose
    private String avatar;
    @SerializedName("public_repos")
    @Expose
    private int repos;
    @SerializedName("created_at")
    @Expose
    private Date created;

    private static SimpleDateFormat sdf;

    static {
        sdf = new SimpleDateFormat(Const.DATE_TIME_FORMAT, Locale.US);
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar + "&size=" + Utils.getAvatarSize(false);
    }

    public int getRepos() {
        return repos;
    }

    public void setRepos(int repos) {
        this.repos = repos;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreated() {
        return sdf.format(created);
    }
}
