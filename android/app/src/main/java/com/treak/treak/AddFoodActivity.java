package com.treak.treak;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.treak.treak.models.FoodItem;


public class AddFoodActivity extends ActionBarActivity {
    EditText foodValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        foodValue = (EditText) findViewById(R.id.food_name_value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save_food_item, menu);
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
        } else if (id == R.id.action_save_food_details) {
            String name = foodValue.getText().toString();
            if (name != null) {
                FoodItem foodItem = new FoodItem(name);
                foodItem.saveInBackground();
                Toast.makeText(this, "Exercise saved to Parse", Toast.LENGTH_LONG);
                finish();
            } else {
                Toast.makeText(this, "Please Enter a Name", Toast.LENGTH_LONG);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
