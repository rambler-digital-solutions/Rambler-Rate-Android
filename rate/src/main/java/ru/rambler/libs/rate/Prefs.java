package ru.rambler.libs.rate;

import android.content.Context;
import android.content.SharedPreferences;

class Prefs {
    private static final String PREFS_FILE_NAME = "ru.rambler.rate";
    private static final String PREFS_INIT_TIMESTAMP = "init_timestamp";
    private static final String PREFS_CANCELED_TIMESTAMP = "canceled_timestamp";

    private final SharedPreferences sharedPreferences;

    private Prefs(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static Prefs newInstance(Context context) {
        return new Prefs(context);
    }

    public long getInitTimestamp() {
        return sharedPreferences.getLong(PREFS_INIT_TIMESTAMP, 0);
    }

    public void setInitTimestamp(long timestamp) {
        sharedPreferences.edit().putLong(PREFS_INIT_TIMESTAMP, timestamp).apply();
    }

    public void setCancelTimestamp(long timestamp) {
        sharedPreferences.edit().putLong(PREFS_CANCELED_TIMESTAMP, timestamp).apply();
    }
}
