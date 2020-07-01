package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

public class BitcoinExchangeRateModel {

    // Private Variables

     String mRate;

    public static BitcoinExchangeRateModel fromJson (JSONObject jsonObject){

        try {
            BitcoinExchangeRateModel bitCoinData = new BitcoinExchangeRateModel();
            bitCoinData.mRate = jsonObject.getString("ask");

            return bitCoinData;
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
