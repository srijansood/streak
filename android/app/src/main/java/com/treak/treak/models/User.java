package com.treak.treak.models;

import com.parse.ParseClassName;
import com.parse.ParseUser;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daryl on 10/3/15.
 */

public class User {

    private long dayStreak;
    private BigDecimal moneyEarned;
    private DateTime lastRecordedTime;
    private String[] gymPreferences = new String[4];

    public long getDayStreak() {
//        return getLong("dayStreak");
        return dayStreak;
    }

    private void streakStillValid() {
        DateTime now = DateTime.now();
        double hours = (lastRecordedTime.getMillis() - now.getMillis())/1000/60/60;
        if (hours > 24) {
            setDayStreak(0);
        }
    }

    public BigDecimal getMoneyEarned() {
//        return (BigDecimal) get("moneyEarned");
        return moneyEarned;
    }

    public void setDayStreak(long dayStreak) {
//        put("dayStreak", dayStreak);
        this.dayStreak = dayStreak;
    }

    public void setMoneyEarned(BigDecimal moneyEarned) {
//        put("moneyEarned", moneyEarned);
        this.moneyEarned = moneyEarned;
    }
}
