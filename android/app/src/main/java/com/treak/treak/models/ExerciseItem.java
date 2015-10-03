package com.treak.treak.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Daryl on 10/3/15.
 */

@ParseClassName("ExerciseItem")
public class ExerciseItem extends ParseObject {

    public ExerciseItem() {
        super();
    }

    public ExerciseItem(String name) {
        super();
        put("exerciseName", name);
    }

    public String getExerciseName() {
        return getString("exerciseName");
    }

    public void setCompletionDate(Date date) {
        put("completionDate", date);
    }

    public Date getCompletionDate() {
        return getDate("completionDate");
    }

}
