package ru.korsander.followgit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import ru.korsander.followgit.FollowGitApp;
import ru.korsander.followgit.R;

/**
 * Created by korsander on 12.07.2015.
 */
public class Utils {
    public static float convertDpToPixel(float dp) {
        float dest = Resources.getSystem().getDisplayMetrics().densityDpi;
        float px = dp * (dest / 160f);
        return px;
    }

    public static float convertPixelsToDp(float px) {
        float dest = Resources.getSystem().getDisplayMetrics().densityDpi;
        float dp = px / (dest / 160f);
        return dp;
    }

    public static int getAvatarSize() {
        if(Const.avatarSize == -1) {
            SharedPreferences settings = FollowGitApp.getContext().getSharedPreferences(Const.APP_SETTINGS, Context.MODE_PRIVATE);
            if(settings.contains(Const.AVATAR_SIZE)) {
                Const.avatarSize = settings.getInt(Const.AVATAR_SIZE, 32);
            } else {
                int dim = (int) FollowGitApp.getContext().getResources().getDimension(R.dimen.list_item_avatar_size);
                Const.avatarSize = (int) convertDpToPixel(dim);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt(Const.AVATAR_SIZE, Const.avatarSize);
                editor.apply();
            }
        }
        return Const.avatarSize;
    }
}
