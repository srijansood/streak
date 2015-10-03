package com.treak.treak.models;

import com.parse.ParseClassName;
import com.parse.ParseUser;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daryl on 10/3/15.
 */

@ParseClassName("User")
public class User extends ParseUser {

    private long dayStreak;
    private BigDecimal moneyEarned;

    public User() {
        super();
    }

    public long getDayStreak() {
        return getLong("dayStreak");
    }

    public BigDecimal getMoneyEarned() {
        return (BigDecimal) get("moneyEarned");
    }

    public void setDayStreak(long dayStreak) {
        put("dayStreak", dayStreak);
    }

    public void setMoneyEarned(BigDecimal moneyEarned) {
        put("moneyEarned", moneyEarned);
    }
}
