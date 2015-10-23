package ru.rambler.rate;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class RamblerRate {
    private static final String FRAGMENT_TAG = RamblerRate.class.getName();
    private static final int REQUEST_CODE = 0x3284;
    public static final int DELAY_INFINITE = -1;
    private int delayDays = 7;
    private int delayOpens = DELAY_INFINITE;

    private static RamblerRate sInstance;

    private int layout;
    private int style;
    private String title;
    private String text;

    private String labelLater;
    private String labelCancel;

    private OnRateClickListener onRateClickListener;

    public interface OnRateClickListener {
        void onRate(int stars);

        void onLaterClick();

        void onCancelClick();
    }

    private RamblerRate() {

    }

    public static synchronized void init(Configuration configuration) {
        if (sInstance != null) {
            throw new IllegalStateException("Rambler-rate already initialized");
        }
        sInstance = configuration.rate;
    }

    public static void startForResult(Context context) {
        if (sInstance == null) {
            throw new IllegalStateException("The method 'init' must be called before");
        }

        Activity activity = (Activity) context;
        activity.startActivityForResult(new Intent(context, null), REQUEST_CODE);
    }

    public static class Configuration {
        private final RamblerRate rate = new RamblerRate();

        public Configuration setDelayDays(int days) {
            rate.delayDays = days;
            return this;
        }
    }

}
