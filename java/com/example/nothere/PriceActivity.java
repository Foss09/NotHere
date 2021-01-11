package com.example.nothere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PriceActivity extends AppCompatActivity implements CurrencyAdapter.Chosen {
    RecyclerView rvPrice;
    ImageButton ibMoneyPrice,ibCheck,ibClose;
    RecyclerView.LayoutManager layoutManager;
    Dialog dialog;
    RecyclerView rvCurrency;
    RecyclerView.LayoutManager layoutManager2;
    RecyclerView.Adapter adapter;
    SharedPreferences.Editor editor;
    SharedPreferences perfs;
    RecyclerView.Adapter adapter2;
    VieweModel vieweModel;
    TextView tvCurrentCurrency,tvCityPriceName,tvError;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        dialog =new Dialog(this);
        dialog.setContentView(R.layout.currency_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        dialog.getWindow().setLayout(1000,3000);

        editor=getSharedPreferences("Currency",MODE_PRIVATE).edit();
        perfs=getSharedPreferences("Currency",MODE_PRIVATE);
        tvCurrentCurrency= dialog.findViewById(R.id.tvCurrentCurrency);
        tvCurrentCurrency.setText(perfs.getString("CurrentCurrency","USD"));
        ibClose=dialog.findViewById(R.id.ibClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ibCheck.setVisibility(View.INVISIBLE);
                tvCurrentCurrency.setText(perfs.getString("CurrentCurrency","USD"));
            }
        });


        ibCheck=dialog.findViewById(R.id.ibCheck);
        ibCheck.setVisibility(View.INVISIBLE);

        rvCurrency=dialog.findViewById(R.id.rvCurrency);
        tvError=findViewById(R.id.tvError);
        layoutManager2=new LinearLayoutManager(this);
        rvCurrency.setLayoutManager(layoutManager2);

        rvPrice=findViewById(R.id.rvPrice);
        layoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        rvPrice.setLayoutManager(layoutManager);
        vieweModel=new VieweModel(this);
        tvCityPriceName=findViewById(R.id.tvCityPriceName);

        ibMoneyPrice=findViewById(R.id.ibMoneyPrice);
        ibMoneyPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


        String tab_name=getIntent().getStringExtra("Table_name");
        String cat=getIntent().getStringExtra("Category");
        String regName=getIntent().getStringExtra("Name");
        SharedPreferences perfs=getSharedPreferences("Currency",MODE_PRIVATE);
        String currency= perfs.getString("CurrentCurrency","USD");

        tvCityPriceName.setText(regName);


        try {
            String rateString = perfs.getString("CurrencyRate",null);
            float rate;
            if (!(rateString==null)){
                if (!rateString.contains(".")){
                    rate =Float.parseFloat(rateString) ;
                }else{
                    rate =Float.parseFloat(rateString.substring(0,rateString.indexOf('.')+2)) ;
                }
            }else{
                rate=0;
                rvPrice.setVisibility(View.INVISIBLE);
                tvCityPriceName.setText("NO INTERNET");
                tvError.setVisibility(View.VISIBLE);
            }




            adapter=new PriceAdapter(vieweModel.getPrices(tab_name,cat),this,currency,rate);
            rvPrice.setAdapter(adapter);
            adapter2=new CurrencyAdapter(this,vieweModel.getCurrencies());
            rvCurrency.setAdapter(adapter2);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void chose(final String s) {
        ///when a currency is chosen from the dialog we save in sharedpreferences and pass it on the Intent
        tvCurrentCurrency.setText(s);
        ibCheck.setVisibility(View.VISIBLE);
        ibCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("CurrentCurrency",s);
                try {
                    editor.putString("CurrencyRate",vieweModel.getRate(s));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                editor.commit();
                ibCheck.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                recreate();
            }
        });

    }
}