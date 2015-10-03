package com.treak.treak;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.treak.treak.helpers.Secrets;
import com.treak.treak.models.ExerciseItem;
import com.treak.treak.models.FoodItem;
import com.treak.treak.models.User;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Register parse models
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(FoodItem.class);
        ParseObject.registerSubclass(ExerciseItem.class);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, Secrets.APPLICATION_ID, Secrets.CLIENT_KEY);

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();


        User user = new User();
        user.setDayStreak(10);
        user.setMoneyEarned(new BigDecimal(10.00));

        Log.d("User streak: ", Long.toString(user.getDayStreak()));
        FoodItem foodItem = new FoodItem("apples");
        foodItem.setCompletionDate(new Date());
        foodItem.saveInBackground();
        // Specify which class to query
        ParseQuery<FoodItem> query = ParseQuery.getQuery(FoodItem.class);
        // Specify the object id
        query.whereEqualTo("foodName", foodItem.getFoodName());
        query.findInBackground(new FindCallback<FoodItem>() {
            @Override
            public void done(List<FoodItem> list, com.parse.ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    Log.d("array length", "Length: " + list.size());
                    String foodName = list.get(0).getFoodName();
                    Log.d("foodName", "Food Name: " + foodName);
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
