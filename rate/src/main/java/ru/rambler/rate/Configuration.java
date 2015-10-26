package ru.rambler.rate;

import android.content.Context;

import java.io.Serializable;


public class Configuration implements Serializable {

    private final transient Context context;
    private int layout = R.layout.activity_rate;
    private int requestCode = 0x7205;
    private String titleText;
    private int titleStringId;
    private int delayDays = 7;
    private int delayOpens = RamblerRate.DELAY_INFINITE;

    private String labelLaterText;
    private int labelLaterStringId = R.string.label_later;
    private String labelCancelText;
    private int labelCancelStringId = R.string.label_cancel;
    private int messageStringId;
    private String messageText;

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

    public int getDelayDays() {
        return delayDays;
    }

    public Configuration setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public void setDelayOpens(int delayOpens) {
        this.delayOpens = delayOpens;
    }

    public String getLabelCancelText() {
        return labelCancelText;
    }

    public Configuration setLabelCancel(String labelCancelText) {
        this.labelCancelText = labelCancelText;
        return this;
    }

    public void setLabelLater(String labelLaterText) {
        this.labelLaterText = labelLaterText;
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

    public Configuration setLabelLater(int labelLaterStringId) {
        this.labelLaterStringId = labelLaterStringId;
        return this;
    }

    public int getLabelCancelStringId() {
        return labelCancelStringId;
    }

    public Configuration setLabelCancel(int labelCancelStringId) {
        this.labelCancelStringId = labelCancelStringId;
        return this;
    }

    public int getMessageStringId() {
        return messageStringId;
    }

    public Configuration setMessage(int messageStringId) {
        this.messageStringId = messageStringId;
        return this;
    }

    public String getMessageText() {
        return messageText;
    }

    public Configuration setMessage(String messageText) {
        this.messageText = messageText;
        return this;
    }
}