package ru.rambler.rate;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import java.util.Date;

public class DateDelayTest extends InstrumentationTestCase {

    private static final long MS_IN_DAY = 1000 * 60 * 60 * 24;

    public void testPrefs() {
        Prefs prefs = Prefs.newInstance(getInstrumentation().getContext());
        long timestamp = prefs.getInitTimestamp();
        Assert.assertTrue(timestamp >= -1);

        prefs.setInitTimestamp(-1);
        Assert.assertEquals(-1, prefs.getInitTimestamp());

        prefs.setInitTimestamp(1225);
        Assert.assertEquals(1225, prefs.getInitTimestamp());
    }

    public void testCutTime() {
        long now = System.currentTimeMillis();
        long today = Utils.eraseTime(now);

        Assert.assertFalse(now == today);
        Assert.assertTrue(now > today);
        Assert.assertTrue(today > 0);

        Date dateNow = new Date(now);
        Date dateToday = new Date(today);
        Assert.assertEquals(dateNow.getDate(), dateToday.getDate());
        Assert.assertEquals(dateNow.getMonth(), dateToday.getMonth());
        Assert.assertEquals(dateNow.getYear(), dateToday.getYear());
    }

    public void testDelay() {
        Prefs prefs = Prefs.newInstance(getInstrumentation().getContext());

        prefs.setInitTimestamp(0);//simulate first call
        RamblerRate.initialize(RamblerRate.Configuration.newInstance(getInstrumentation().getContext()).setDelayDays(1));
        Assert.assertFalse(RamblerRate.canShow(getInstrumentation().getContext()));

        prefs.setInitTimestamp(0);//simulate first call
        RamblerRate.initialize(RamblerRate.Configuration.newInstance(getInstrumentation().getContext()).setDelayDays(0));
        Assert.assertTrue(RamblerRate.canShow(getInstrumentation().getContext()));

        prefs.setInitTimestamp(Utils.eraseTime(System.currentTimeMillis() - MS_IN_DAY));//simulate first call yesterday
        RamblerRate.initialize(RamblerRate.Configuration.newInstance(getInstrumentation().getContext()).setDelayDays(1));
        Assert.assertTrue(RamblerRate.canShow(getInstrumentation().getContext()));

        prefs.setInitTimestamp(Utils.eraseTime(System.currentTimeMillis() - MS_IN_DAY));//simulate first call yesterday
        RamblerRate.initialize(RamblerRate.Configuration.newInstance(getInstrumentation().getContext()).setDelayDays(2));
        Assert.assertFalse(RamblerRate.canShow(getInstrumentation().getContext()));
    }
}
