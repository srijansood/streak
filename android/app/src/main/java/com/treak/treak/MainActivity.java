package com.treak.treak;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;

import com.getpebble.android.kit.PebbleKit;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.treak.treak.helpers.MyYAxisValueFormatter;
import com.treak.treak.helpers.Secrets;
import com.treak.treak.models.ExerciseItem;
import com.treak.treak.models.FoodItem;
import com.treak.treak.models.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity implements SeekBar.OnSeekBarChangeListener, LocationListener {

    public static User userModel;
    private BarChart mChart;
    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    private LocationManager locationManager;
    private EditText streakValue;
    private EditText cashValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        streakValue = (EditText) findViewById(R.id.streak_value);
        cashValue = (EditText) findViewById(R.id.cash_value);
        streakValue.setKeyListener(null);
        cashValue.setKeyListener(null);

        userModel = new User();
        setupParse();
        setupChart();
        setLabels();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    private void setLabels() {
        Log.d("Day Streak: ", Long.toString(userModel.getDayStreak()));
        streakValue.setText("Your current streak is " + Long.toString(userModel.getDayStreak()) + " days.");
        cashValue.setText("You've earned $" + userModel.getMoneyEarned().toString() + " dollars!");
    }

    private void setupParse() {
        // Register parse models
        ParseObject.registerSubclass(FoodItem.class);
        ParseObject.registerSubclass(ExerciseItem.class);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, Secrets.APPLICATION_ID, Secrets.CLIENT_KEY);


        userModel.setDayStreak(200);
        userModel.setMoneyEarned(new BigDecimal(200.00));

        Log.d("User streak: ", Long.toString(userModel.getDayStreak()));
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

    private void setupChart() {
        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setDescription(null);

        //Only show results for the past year
        mChart.setMaxVisibleValueCount(12);

        //Scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        mChart.animateY(2500);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);

        setData(12, 15);
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
        if (id == R.id.action_add_exercise_food) {
            startAddExerciseActivity();
        } else if (id == R.id.action_view_capital_one) {
            startCapitalOneActivity();
        } else if (id == R.id.action_view_leaderboards) {
            startListActivity();
        } else if (id == R.id.action_view_metrics) {
            startMetricsActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void startMetricsActivity() {
        Intent intent = new Intent(this, MetricsActivity.class);
        startActivity(intent);
    }

    private void startListActivity() {
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }

    private void startCapitalOneActivity() {
        Intent intent = new Intent(this, CapitalOneActivity.class);
        intent.putExtra("streak", userModel.getDayStreak());
        Log.d("cashBalance", userModel.getMoneyEarned().toString());
        intent.putExtra("cashBalance", userModel.getMoneyEarned());
        startActivity(intent);
    }

    private void startAddExerciseActivity() {
        Intent intent = new Intent(this, AddFoodExerciseActivity.class);
        intent.putExtra("streak", userModel.getDayStreak());
        intent.putExtra("cashBalance", userModel.getMoneyEarned());
        startActivity(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(mMonths[i % 12]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (i * mult);
            yVals1.add(new BarEntry(val, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "$ Earned");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        mChart.setData(data);
    }

    @Override
        public void onLocationChanged(Location location) {
            boolean isConnected = PebbleKit.isWatchConnected(getBaseContext());

            String msg2 = "";
            String msg1 = "";

            final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");

            if (isConnected) {
                msg1 = "Dining hall detected. Are you eating out today?";
            }
                msg2 = "Fitness Center detected. Ae you going to work out?";

            final Map data = new HashMap();
            data.put("title", "Streak Message");
            data.put("body", msg1);
            final JSONObject jsonData = new JSONObject(data);
            final String notificationData = new JSONArray().put(jsonData).toString();

            i.putExtra("messageType", "PEBBLE_ALERT");
            i.putExtra("sender", "PebbleKit Android");
            i.putExtra("notificationData", notificationData);
            sendBroadcast(i);

            data.put("title", "Streak Message");
            data.put("body", msg2);
            final JSONObject jsonData2 = new JSONObject(data);
            final String notificationData2 = new JSONArray().put(jsonData2).toString();

            i.putExtra("messageType", "PEBBLE_ALERT");
            i.putExtra("sender", "PebbleKit Android");
            i.putExtra("notificationData", notificationData2);
            sendBroadcast(i);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("Latitude", "disable");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("Latitude", "enable");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Latitude","status");
        }

        @Override
        protected void onResume() {
            super.onResume();
        }}
