package ru.korsander.followgit.utils;

/**
 * Created by korsander on 12.07.2015.
 */
public class Const {
    public static final String APP_SETTINGS = "settings";
    public static final String LAST_QUERY = "last_query";
    public static final String AVATAR_SIZE = "asize";
    public static final String AVATAR_DIALOG_SIZE = "adsize";
    public static int avatarSize = -1;
    public static int avatarDialogSize = -1;
    public static final String API_PATH = "https://api.github.com";
    public static final String GITHUB_PATH = "https://github.com/";
    public static final String DATE_TIME_FORMAT = "HH:mm:ss dd.MM.yyyy";

    //http timeouts
    public static final int CONNECTION_TIMEOUT    = 120;
    public static final int READ_TIMEOUT          = 240;

    public static final int LIMIT = 100;
}
