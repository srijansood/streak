package com.treak.treak.models;

import com.parse.ParseClassName;
import com.parse.ParseUser;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daryl on 10/3/15.
 */

public class User {

    private long dayStreak;
    private BigDecimal moneyEarned;

    public long getDayStreak() {
//        return getLong("dayStreak");
        return dayStreak;
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
