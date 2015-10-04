package com.treak.treak;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.treak.treak.helpers.Secrets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;


public class CapitalOneActivity extends ActionBarActivity {

    private EditText balanceValue;
    private EditText accountTypeValue;
    private EditText rewardsValue;
    private Button depositButton;
    private RequestQueue requestQueue;
    private JSONObject apiResponseArray;
    private String balance;
    private String type;
    private BigDecimal rewards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capital_one);

        balanceValue = (EditText) findViewById(R.id.balance_value);
        accountTypeValue = (EditText) findViewById(R.id.account_type_value);
        rewardsValue = (EditText) findViewById(R.id.rewards_value);
        balanceValue.setKeyListener(null);
        accountTypeValue.setKeyListener(null);
        rewardsValue.setKeyListener(null);

        requestQueue = Volley.newRequestQueue(this);
        depositButton = (Button) findViewById(R.id.deposit_button);
        rewards = (BigDecimal) getIntent().getExtras().get("cashBalance");

        depositButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (rewards.equals(new BigDecimal(0))) {
                    Toast.makeText(getApplicationContext(), "You have no money!!! Work out/eat healthy to keep a streak going!", Toast.LENGTH_SHORT).show();
                } else {
                    String currentBalanceString = balanceValue.getText().toString();
                    long currentBalance = Long.parseLong(currentBalanceString.substring(currentBalanceString.lastIndexOf("$") + 1));
                    balance = Long.toString(currentBalance + rewards.longValue());
                    balanceValue.setText("Your balance is $" + balance);
                    rewards = new BigDecimal(0);
                    rewardsValue.setText("Your rewards balance is $0");
                }
            }
        });

        getCapitalOneAccounts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_capital_one, menu);
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

    public void getCapitalOneAccounts() {
        StringBuilder urlBuilder = new StringBuilder("http://api.reimaginebanking.com/accounts?key=")
                .append(Secrets.CAPITAL_ONE_API_KEY);

        String url = new String(urlBuilder);

        Response.ErrorListener responseErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null && error.getMessage() != null) {
                    Log.d("Error Response", error.getMessage());
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener() {

                    @Override
                    public void onResponse(Object response) {
                        try {
                            JSONArray capitalOneApiResponse = new JSONArray((String) response);
                            parseApiResponse(capitalOneApiResponse);
                            setLabels();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, responseErrorListener);

        requestQueue.add(stringRequest);
    }

    private void setLabels() {
        balanceValue.setText("Your balance is $" + balance);
        accountTypeValue.setText("This is your " + type + " account.");
        rewardsValue.setText("Your rewards balance is $" + rewards + ".");
    }

    private void parseApiResponse(JSONArray capitalOneApiResponse) {
        try {
            apiResponseArray = capitalOneApiResponse.getJSONObject(2);
            balance = apiResponseArray.get("balance").toString();
            type = apiResponseArray.get("type").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
