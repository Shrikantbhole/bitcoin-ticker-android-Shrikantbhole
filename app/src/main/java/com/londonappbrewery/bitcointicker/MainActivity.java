package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    // Member Variables:
    TextView mPriceTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);
                Log.d("Bitcoin", "" + parent.getItemAtPosition(position));
                String Base_URL = URL + parent.getItemAtPosition(position);
                Log.d("Bitcoin","The URL is " + Base_URL );

                try {
                    letsDoSomeNetworking(Base_URL);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Bitcoin", "Nothing selected");
            }
        });

    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url) throws IOException, ClassNotFoundException {


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("BitCoin","Success! JSON" + response.toString());

                BitcoinExchangeRateModel BitCoinRate = BitcoinExchangeRateModel.fromJson(response);


                mPriceTextView.setText(BitCoinRate.mRate);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("BitCoin", "Failed connecting to Server " + throwable.toString());
                Log.d("BitCoin", "StatusCode" + statusCode);

            }

        });


    }


}
