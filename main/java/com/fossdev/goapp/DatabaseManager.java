package com.fossdev.goapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseManager {
    private static String DATA_BASE_NAME = "nothere_db.db";
    private static int DATA_BASE_VERSION = 1;
    private DBhelper dBhelper;
    private Context context;
    private SQLiteDatabase theDataBase;

    public DatabaseManager(Context con) {
        context = con;
    }

    private class DBhelper extends SQLiteAssetHelper {
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            super.onUpgrade(db, oldVersion, newVersion);
        }

        public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

    }

    public DatabaseManager open() {
        dBhelper = new DBhelper(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        theDataBase = dBhelper.getReadableDatabase();
        return this;
    }

    public void close() {
        dBhelper.close();
    }

    public ArrayList<String[]> cityNames() {
        //this function is to get all the city names in our data base
        String[] cols = {"Name", "Country", "Table_name"};
        Cursor c = theDataBase.query("cities_table", cols, null, null, null, null, null);
        ArrayList<String[]> resultNames = new ArrayList<String[]>();
        int iName = c.getColumnIndex("Name");
        int iCountry = c.getColumnIndex("Country");
        int iTableName = c.getColumnIndex("Table_name");
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String[] row = new String[3];
            row[0] = c.getString(iName);
            row[1] = c.getString(iCountry);
            row[2] = c.getString(iTableName);
            resultNames.add(row);
        }
        return resultNames;

    }

    public ArrayList<String[]> getPrices(String tableName, String category) {
        //in here we pass the query to get our prices based on the category from the city table that we wish
        String[] cols = {"Name", "Price"};
        Cursor c = theDataBase.query(tableName, cols, "Category= \"" + category + "\"", null, null, null, null, null);
        int iName = c.getColumnIndex("Name");
        int iPrice = c.getColumnIndex("Price");
        ArrayList<String[]> results = new ArrayList<String[]>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String[] row = new String[2];
            row[0] = c.getString(iName);
            row[1] = c.getString(iPrice);
            results.add(row);
        }
        return results;
    }

    public ArrayList<String> currencies() {
        //this function is to get all the currencies names in our data base
        String[] cols = {"Name"};
        Cursor c = theDataBase.query("exchange_table", cols, null, null, null, null, null);
        ArrayList<String> resultcurrencies = new ArrayList<String>();
        int iName = c.getColumnIndex("Name");
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            resultcurrencies.add(c.getString(iName));
        }
        return resultcurrencies;
    }

    public String rate(String currency) {
        ///As the name hints, it's to get the appropriate exchange rate for the wanted currency against the USD
        String[] cols = {"Rate"};
        Cursor c = theDataBase.query("exchange_table", cols, "Name= \"" + currency + "\"", null, null, null, null, null);
        int iRate = c.getColumnIndex("Rate");
        String result = "";
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result += c.getString(iRate);
        }
        return result;
    }
}

