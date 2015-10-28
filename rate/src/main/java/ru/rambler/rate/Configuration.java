package ru.rambler.rate;

import android.content.Context;

import java.io.Serializable;


public class Configuration implements Serializable {

    private final transient Context context;
    private int layout = R.layout.activity_rate;
    private int theme = R.style.RateTheme;
    private int requestCode = 0x7205;
    private String titleText;
    private int titleStringId;
    private int daysNotShow = 7;

    private String labelLaterText;
    private int labelLaterStringId = R.string.label_later;
    private String labelCancelText;
    private int labelCancelStringId = R.string.label_cancel;
    private int messageStringId;
    private String messageText;
    private int iconId = android.R.drawable.sym_def_app_icon;

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

    /**
     * set count of days while dialog shouldn't be shown
     */
    public Configuration setDaysNotShow(int days) {
        this.daysNotShow = days;
        return this;
    }

    public int getDaysNotShow() {
        return daysNotShow;
    }

    public String getLabelCancelText() {
        return labelCancelText;
    }

    public int getLabelCancelStringId() {
        return labelCancelStringId;
    }

    public Configuration setLabelCancel(int labelCancelStringId) {
        this.labelCancelStringId = labelCancelStringId;
        return this;
    }

    public Configuration setLabelCancel(String labelCancelText) {
        this.labelCancelText = labelCancelText;
        return this;
    }

    public String getLabelLaterText() {
        return labelLaterText;
    }

    public Configuration setLayout(int layout) {
        this.layout = layout;
        return this;
    }

    public int getLayout() {
        return layout;
    }

    public Configuration setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public Configuration setTitle(int titleStringId) {
        this.titleStringId = titleStringId;
        return this;
    }

    public int getTitleStringId() {
        return titleStringId;
    }

    public Configuration setTitle(String titleText) {
        this.titleText = titleText;
        return this;
    }

    public String getTitleText() {
        return titleText;
    }

    Context getContext() {
        return context;
    }

    public int getLabelLaterStringId() {
        return labelLaterStringId;
    }

    public Configuration setLabelLater(String labelLaterText) {
        this.labelLaterText = labelLaterText;
        return this;
    }

    public Configuration setLabelLater(int labelLaterStringId) {
        this.labelLaterStringId = labelLaterStringId;
        return this;
    }

    public Configuration setMessage(int messageStringId) {
        this.messageStringId = messageStringId;
        return this;
    }

    public Configuration setMessage(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public int getMessageStringId() {
        return messageStringId;
    }

    public String getMessageText() {
        return messageText;
    }

    public int getIconId() {
        return iconId;
    }

    public Configuration setIconId(int iconId) {
        this.iconId = iconId;
        return this;
    }

    public int getTheme() {
        return theme;
    }

    public Configuration setTheme(int theme) {
        this.theme = theme;
        return this;
    }
}
