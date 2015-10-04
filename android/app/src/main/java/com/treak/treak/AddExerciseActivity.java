package com.treak.treak;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.treak.treak.helpers.UtilityMethods;
import com.treak.treak.models.ExerciseItem;
import com.treak.treak.models.User;

public class AddExerciseActivity extends ActionBarActivity {

    EditText exerciseValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        exerciseValue = (EditText) findViewById(R.id.exercise_name_value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save_exercise_item, menu);
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
        } else if (id == R.id.action_save_exercise_details) {
            String name = exerciseValue.getText().toString();
            if (name != null) {
                ExerciseItem exerciseItem = new ExerciseItem(name);
                exerciseItem.saveInBackground();
                finish();
            } else {
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
