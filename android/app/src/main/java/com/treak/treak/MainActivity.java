package com.treak.treak;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.treak.treak.helpers.MyYAxisValueFormatter;
import com.treak.treak.helpers.Secrets;
import com.treak.treak.models.ExerciseItem;
import com.treak.treak.models.FoodItem;
import com.treak.treak.models.User;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity implements SeekBar.OnSeekBarChangeListener {

    private BarChart mChart;
    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setupParse();
        setupChart();

    }

    private void setupParse() {
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

    private void setupChart() {
        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setDescription("$ Earned in Past Months");

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

//        Legend l = mChart.getLegend();
//        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        setData(12, 50);
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
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(val, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        mChart.setData(data);
    }
}
