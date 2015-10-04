package com.treak.treak;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class MetricsActivity extends ActionBarActivity {

    private EditText longestStreakValue;
    private EditText averageStreakValue;
    private EditText nextMilestoneValue;
    private EditText nextRewardValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);

        longestStreakValue = (EditText) findViewById(R.id.longest_streak_value);
        averageStreakValue = (EditText) findViewById(R.id.average_streak_value);
        nextMilestoneValue = (EditText) findViewById(R.id.next_milestone_value);
        nextRewardValue = (EditText) findViewById(R.id.next_reward_value);
        longestStreakValue.setKeyListener(null);
        averageStreakValue.setKeyListener(null);
        nextMilestoneValue.setKeyListener(null);
        nextRewardValue.setKeyListener(null);

        longestStreakValue.setText("Longest Streak: 217 days");
        averageStreakValue.setText("Average Streak: 165 days");
        nextMilestoneValue.setText("Next Milestone: 215 days");
        nextRewardValue.setText("Next Reward Value: $9");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_metrics, menu);
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
