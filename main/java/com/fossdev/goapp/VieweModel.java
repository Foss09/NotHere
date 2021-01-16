package com.fossdev.goapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class VieweModel extends ViewModel {
    private DatabaseManager databaseManager;
    private Context context;

    public VieweModel(Context con) {
        context = con;
        databaseManager = new DatabaseManager(context);
    }

    public ArrayList<String[]> getCities() throws ExecutionException, InterruptedException {
        //this function links to our background task, and both of them are aiming to get the names of all the cities in the database
        CitiesGetter citiesGetter = new CitiesGetter();

        return citiesGetter.execute().get();
    }

    public ArrayList<String[]> getPrices(String tableName, String categ) throws ExecutionException, InterruptedException {
        //this function is linked to our background task, and both of them are aiming to get the prices of stuff after filtering down
        //filters are : cate->category of the product , tableName -> the city table in our data base ::what city did we select
        //both filters are selected in the activities
        String[] args = new String[2];
        args[0] = tableName;
        args[1] = categ;
        PriceGetter priceGetter = new PriceGetter();
        return priceGetter.execute(args).get();
    }

    public ArrayList<String> getCurrencies() throws ExecutionException, InterruptedException {
        return new CurrenciesGetter().execute().get();

    }

    public String getRate(String currency) throws ExecutionException, InterruptedException {
        return new RateGetter().execute(currency).get();
    }

    public class CitiesGetter extends AsyncTask<Void, Void, ArrayList<String[]>> {

        @Override
        protected ArrayList<String[]> doInBackground(Void... voids) {
            databaseManager.open();

            return databaseManager.cityNames();
        }
    }

    public class PriceGetter extends AsyncTask<String[], Void, ArrayList<String[]>> {

        @Override
        protected ArrayList<String[]> doInBackground(String[]... voids) {
            String[] args = voids[0];
            databaseManager.open();

            return databaseManager.getPrices(args[0], args[1]);
        }
    }

    public class CurrenciesGetter extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            databaseManager.open();
            return databaseManager.currencies();
        }
    }

    public class RateGetter extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://v6.exchangerate-api.com/v6/7298c00edad1769469b7957c/latest/USD");
                InputStream inputStream = ((HttpURLConnection) url.openConnection()).getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                JSONObject jsonString = new JSONObject(scanner.next());
                JSONObject objy = jsonString.getJSONObject("conversion_rates");
                return Double.toString(objy.getDouble(strings[0]));

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }
    }


}

