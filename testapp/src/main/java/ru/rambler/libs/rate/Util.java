package ru.rambler.libs.rate;

import android.content.Context;

import ru.rambler.libs.rate.RamblerRate;


public class Util {
    public static void resetRamblerRate(Context context) {
        RamblerRate.reset(context);
    }
}
