package ru.rambler.rate;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class RamblerRate {

    private static final long MS_IN_DAY = 1000 * 60 * 60 * 24;
    public static final int DELAY_INFINITE = -1;
    private static final int TIMESTAMP_NOT_REMIND_MORE = -1;

    private static RamblerRate instance;
    private final Configuration configuration;


    public interface Callback {
        void rated(int stars);

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

        return System.currentTimeMillis() > initTimestamp + MS_IN_DAY * instance.configuration.getDelayDays();
    }

    private static Intent createIntent(Context context, Configuration configuration) {
        Intent intent = new Intent(context, RateActivity.class);
        intent.putExtra(RateActivity.ARG_LAYOUT, configuration.getLayout());
        intent.putExtra(RateActivity.ARG_TITLE_TEXT, configuration.getTitleText());
        intent.putExtra(RateActivity.ARG_TITLE_STRING_ID, configuration.getTitleStringId());
        intent.putExtra(RateActivity.ARG_LABEL_CANCEL_STRING_ID, configuration.getLabelCancelStringId());
        intent.putExtra(RateActivity.ARG_LABEL_CANCEL_TEXT, configuration.getLabelCancelText());
        intent.putExtra(RateActivity.ARG_LABEL_LATER_STRING_ID, configuration.getLabelLaterStringId());
        intent.putExtra(RateActivity.ARG_LABEL_LATER_TEXT, configuration.getLabelLaterText());

        return intent;
    }

    public static boolean onActivityResult(int requestCode, int resultCode, Intent data, Callback callback) {
        checkInstance();
        if (callback == null) {
            throw new IllegalStateException("callback must be set");
        }
        if (requestCode != instance.configuration.requestCode) {
            return false;
        }

        if (resultCode == RateActivity.RESULT_CODE_LATER) {
            Prefs.newInstance(instance.configuration.getContext()).setInitTimestamp(Utils.eraseTime(System.currentTimeMillis()));
            callback.delayed();
        } else if (requestCode == RateActivity.RESULT_CODE_CANCEL) {
            Prefs.newInstance(instance.configuration.getContext()).setInitTimestamp(TIMESTAMP_NOT_REMIND_MORE);
            callback.canceled();
        } else if (requestCode == RateActivity.RESULT_CODE_RATED) {
            Prefs.newInstance(instance.configuration.getContext()).setInitTimestamp(TIMESTAMP_NOT_REMIND_MORE);
            int stars = data.getIntExtra(RateActivity.EXTRA_STARS, 0);
            callback.rated(stars);
        }

        return true;
    }

    private static void checkInstance() {
        if (instance == null) {
            throw new IllegalStateException("The method 'initialize' must be called before");
        }
    }

    public static class Configuration {

        private final Context context;
        private int layout = R.layout.activity_rate;
        private int requestCode = 0x7205;
        private String titleText;
        private int titleStringId;
        private int delayDays = 7;
        private int delayOpens = DELAY_INFINITE;

        private String labelLaterText;
        private int labelLaterStringId = R.string.label_later;
        private String labelCancelText;
        private int labelCancelStringId = R.string.label_cancel;

        private Configuration(Context context) {
            this.context = context;
            Prefs prefs = Prefs.newInstance(context);
            if (prefs.getInitTimestamp() == 0L) {
                prefs.setInitTimestamp(Utils.eraseTime(System.currentTimeMillis()));
            }
        }

        public static Configuration newInstance(Context context) {
            return new Configuration(context);
        }

        public Configuration setDelayDays(int days) {
            this.delayDays = days;
            return this;
        }

        public Configuration setTitle(String title) {
            this.titleText = title;
            return this;
        }

        public Configuration setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public int getDelayDays() {
            return delayDays;
        }

        public int getDelayOpens() {
            return delayOpens;
        }

        public void setDelayOpens(int delayOpens) {
            this.delayOpens = delayOpens;
        }

        public String getLabelCancelText() {
            return labelCancelText;
        }

        public void setLabelCancelText(String labelCancelText) {
            this.labelCancelText = labelCancelText;
        }

        public String getLabelLaterText() {
            return labelLaterText;
        }

        public void setLabelLaterText(String labelLaterText) {
            this.labelLaterText = labelLaterText;
        }

        public int getLayout() {
            return layout;
        }

        public void setLayout(int layout) {
            this.layout = layout;
        }

        public int getRequestCode() {
            return requestCode;
        }

        public int getTitleStringId() {
            return titleStringId;
        }

        public void setTitleStringId(int titleStringId) {
            this.titleStringId = titleStringId;
        }

        public String getTitleText() {
            return titleText;
        }

        public void setTitleText(String titleText) {
            this.titleText = titleText;
        }

        Context getContext() {
            return context;
        }

        public int getLabelLaterStringId() {
            return labelLaterStringId;
        }

        public void setLabelLaterStringId(int labelLaterStringId) {
            this.labelLaterStringId = labelLaterStringId;
        }

        public int getLabelCancelStringId() {
            return labelCancelStringId;
        }

        public void setLabelCancelStringId(int labelCancelStringId) {
            this.labelCancelStringId = labelCancelStringId;
        }
    }
}
