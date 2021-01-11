package com.example.nothere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    TextView tvCurrency,tvCurrentCurrency,tvCurrencyName;
    ImageButton ibMoney,ibCheck,ibClose;
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

        dialog =new Dialog(this);
        dialog.setContentView(R.layout.currency_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        dialog.getWindow().setLayout(1000,3000);

        editor=getSharedPreferences("Currency",MODE_PRIVATE).edit();
        perfs=getSharedPreferences("Currency",MODE_PRIVATE);
        tvCurrentCurrency= dialog.findViewById(R.id.tvCurrentCurrency);
        tvCurrentCurrency.setText(perfs.getString("CurrentCurrency","USD"));


        tvCurrency=findViewById(R.id.tvCurrency);
        ibClose=dialog.findViewById(R.id.ibClose);

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ibCheck.setVisibility(View.INVISIBLE);
                tvCurrentCurrency.setText(perfs.getString("CurrentCurrency","USD"));
            }
        });

        tvCurrencyName=findViewById(R.id.tvCurrencyName);
        ibCheck=dialog.findViewById(R.id.ibCheck);
        ibCheck.setVisibility(View.INVISIBLE);
        ibMoney=findViewById(R.id.ibMoney);

        ibMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        rvCurrency=dialog.findViewById(R.id.rvCurrency);
        layoutManager=new LinearLayoutManager(this);
        rvCurrency.setLayoutManager(layoutManager);
        ibStart=findViewById(R.id.ibStart);
        vieweModel=new VieweModel(this);



        try {
            adapter=new CurrencyAdapter(this,vieweModel.getCurrencies());
            rvCurrency.setAdapter(adapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ibStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,ToActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        //to refresh the currency in case it would be changed at "PriceActivity"
        super.onRestart();
        recreate();
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
            }
        });



    }
}