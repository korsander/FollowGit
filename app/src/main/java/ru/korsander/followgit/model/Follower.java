package ru.korsander.followgit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.korsander.followgit.utils.Utils;

/**
 * Created by korsander on 12.07.2015.
 */
public class Follower {
    @Expose
    private String login;
    @SerializedName("avatar_url")
    @Expose
    private String avatar;

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
        this.avatar = avatar + "&size=" + Utils.getAvatarSize(true);
    }
}
