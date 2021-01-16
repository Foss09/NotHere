package com.fossdev.goapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements CurrencyAdapter.Chosen {
    //In this Activity:
    //The user should choose the currency they prefer
    //And the way they have to take to access other activities, the reason is UX relevant not technical one

    TextView tvCurrency, tvCurrentCurrency, tvCurrencyName;
    ImageButton ibMoney, ibCheck, ibClose;
    Dialog dialog;
    Button ibStart;
    RecyclerView rvCurrency;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    SharedPreferences.Editor editor;
    SharedPreferences perfs;
    VieweModel vieweModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //***************************EXTRAS**************************
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != getPackageManager().PERMISSION_GRANTED) {
            String[] perm = {Manifest.permission.INTERNET};
            ActivityCompat.requestPermissions(this, perm, 100);
        }
        editor = getSharedPreferences("Currency", MODE_PRIVATE).edit();

        perfs = getSharedPreferences("Currency", MODE_PRIVATE);

        atFirstRun();


        //**************************************************************

        //*************DIALOG************************

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.currency_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        dialog.getWindow().setLayout(1000, 3000);

        tvCurrentCurrency = dialog.findViewById(R.id.tvCurrentCurrency);
        ibClose = dialog.findViewById(R.id.ibClose);
        ibCheck = dialog.findViewById(R.id.ibCheck);
        rvCurrency = dialog.findViewById(R.id.rvCurrency);

        ibCheck.setVisibility(View.INVISIBLE);
        tvCurrentCurrency.setText(perfs.getString("CurrentCurrency", "USD"));
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ibCheck.setVisibility(View.INVISIBLE);
                tvCurrentCurrency.setText(perfs.getString("CurrentCurrency", "USD"));
            }
        });

        //**********************************************


        //*********MAIN********************
        tvCurrency = findViewById(R.id.tvCurrency);
        tvCurrencyName = findViewById(R.id.tvCurrencyName);
        ibCheck = dialog.findViewById(R.id.ibCheck);
        tvCurrencyName = findViewById(R.id.tvCurrencyName);
        ibMoney = findViewById(R.id.ibMoney);

        ibMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        //************************************


        //**************RECYCLERVIEW*******************
        layoutManager = new LinearLayoutManager(this);
        rvCurrency.setLayoutManager(layoutManager);
        ibStart = findViewById(R.id.ibStart);

        vieweModel = new VieweModel(this);



        try {
            adapter = new CurrencyAdapter(this, vieweModel.getCurrencies());
            rvCurrency.setAdapter(adapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ibStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ToActivity.class);
                startActivity(intent);
            }
        });
    }
    //****************************************************

    @Override
    protected void onRestart() {
        //to refresh the currency in case it would be changed at "PriceActivity"
        super.onRestart();
        recreate();
    }

    @Override
    public void chose(final String s) {
        ///when a currency is chosen from the dialog we save in sharedpreferences and pass it on to the Intent
        tvCurrentCurrency.setText(s);
        ibCheck.setVisibility(View.VISIBLE);
        ibCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("CurrentCurrency", s);
                try {
                    editor.putString("CurrencyRate", vieweModel.getRate(s));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                editor.commit();
                ibCheck.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        });


    }
    void atFirstRun(){
        //first time the app is opened
        if(perfs.getBoolean("opened",false)==false){
            editor.putString("CurrentCurrency","USD");
            editor.putString("CurrencyRate","1.00");
            editor.putBoolean("opened",true);
            editor.commit();

        }
    }

}