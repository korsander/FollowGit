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

    public static int getAvatarSize(boolean type) {
        String name = type ? Const.AVATAR_SIZE : Const.AVATAR_DIALOG_SIZE;
        int size = type ? Const.avatarSize : Const.avatarDialogSize;

        if(size == -1) {
            SharedPreferences settings = FollowGitApp.getContext().getSharedPreferences(Const.APP_SETTINGS, Context.MODE_PRIVATE);
            if(settings.contains(Const.AVATAR_SIZE)) {
                if(type) {
                    Const.avatarSize = settings.getInt(Const.AVATAR_SIZE, 40);
                    size = Const.avatarSize;
                } else {
                    Const.avatarDialogSize = settings.getInt(Const.AVATAR_DIALOG_SIZE, 200);
                    size = Const.avatarDialogSize;
                }
            } else {
                int dim = (int) FollowGitApp.getContext().getResources().getDimension(type ? R.dimen.list_item_avatar_size : R.dimen.dialog_avatar_size);
                SharedPreferences.Editor editor = settings.edit();
                if(type) {
                    Const.avatarSize = (int) convertDpToPixel(dim);
                    editor.putInt(Const.AVATAR_SIZE, Const.avatarSize);
                    size = Const.avatarSize;
                } else {
                    Const.avatarDialogSize = (int) convertDpToPixel(dim);
                    editor.putInt(Const.AVATAR_DIALOG_SIZE, Const.avatarDialogSize);
                    size = Const.avatarDialogSize;
                }

                editor.apply();
            }
        }
        return size;
    }
}
