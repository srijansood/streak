package com.treak.treak.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Daryl on 10/3/15.
 */

@ParseClassName("FoodItem")
public class FoodItem extends ParseObject {

    public FoodItem() {
        super();
    }

    public FoodItem(String name) {
        super();
        put("foodName", name);
    }

    public String getFoodName() {
        return getString("foodName");
    }

    public void setCompletionDate(Date date) {
        put("completionDate", date);
    }

    public Date getCompletionDate() {
        return getDate("completionDate");
    }
}
