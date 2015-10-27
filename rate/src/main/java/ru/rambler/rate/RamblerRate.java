package ru.rambler.rate;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class RamblerRate {

    private static final long MS_IN_DAY = 1000 * 60 * 60 * 24;
    private static final int TIMESTAMP_NOT_REMIND_MORE = -1;

    private static RamblerRate instance;
    private final Configuration configuration;


    public interface Callback {
        void rated(float stars);

        void delayed();

        void canceled();
    }

    private RamblerRate(Configuration configuration) {
        this.configuration = configuration;
    }

    public static void initialize(Configuration configuration) {
        instance = new RamblerRate(configuration);
    }

    public static void startForResult(Activity activity) {
        checkInstance();
        if (!canShow(activity)) {
            return;
        }
        Intent intent = createIntent(activity, instance.configuration);
        activity.startActivityForResult(intent, instance.configuration.getRequestCode());
    }

    public static void startForResult(Fragment fragment) {
        checkInstance();
        if (!canShow(fragment.getContext())) {
            return;
        }
        Intent intent = createIntent(fragment.getContext(), instance.configuration);
        fragment.startActivityForResult(intent, instance.configuration.getRequestCode());
    }

    static boolean canShow(Context context) {
        long initTimestamp = Prefs.newInstance(context).getInitTimestamp();
        if (initTimestamp == TIMESTAMP_NOT_REMIND_MORE) {
            return false;
        }

        return System.currentTimeMillis() > initTimestamp + MS_IN_DAY * instance.configuration.getDaysNotShow();
    }

    static void reset(Context context) {
        Prefs.newInstance(context).setInitTimestamp(0);
    }

    private static Intent createIntent(Context context, Configuration configuration) {
        Intent intent = new Intent(context, RateActivity.class);
        intent.putExtra(RateActivity.ARG_CONFIG, configuration);
        return intent;
    }

    public static boolean onActivityResult(int requestCode, int resultCode, Intent data, Callback callback) {
        checkInstance();
        if (callback == null) {
            throw new IllegalStateException("callback must be set");
        }
        if (requestCode != instance.configuration.getRequestCode()) {
            return false;
        }

        if (resultCode == RateActivity.RESULT_CODE_LATER) {
            Prefs.newInstance(instance.configuration.getContext()).setInitTimestamp(Utils.eraseTime(System.currentTimeMillis()));
            callback.delayed();
        } else if (resultCode == RateActivity.RESULT_CODE_CANCEL) {
            Prefs.newInstance(instance.configuration.getContext()).setInitTimestamp(TIMESTAMP_NOT_REMIND_MORE);
            callback.canceled();
        } else if (resultCode == RateActivity.RESULT_CODE_RATED) {
            Prefs.newInstance(instance.configuration.getContext()).setInitTimestamp(TIMESTAMP_NOT_REMIND_MORE);
            float stars = data.getFloatExtra(RateActivity.EXTRA_STARS, 0);
            callback.rated(stars);
        }

        return true;
    }

    private static void checkInstance() {
        if (instance == null) {
            throw new IllegalStateException("The method 'initialize' must be called before");
        }
    }
}
