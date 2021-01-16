package com.fossdev.goapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class OfActivity extends AppCompatActivity {

    TextView tvFood, tvTransport, tvGoods, tvMarkets;
    ImageButton ibFood, ibTransport, ibGoods, ibMarkets;
    ImageView ivOf;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_of);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        ivOf = findViewById(R.id.ivOf);
        tvFood = findViewById(R.id.tvFood);
        tvTransport = findViewById(R.id.tvTransport);
        tvGoods = findViewById(R.id.tvGoods);
        tvMarkets = findViewById(R.id.tvMarkets);
        ibFood = findViewById(R.id.ibFood);
        ibTransport = findViewById(R.id.ibTransport);
        ibGoods = findViewById(R.id.ibGoods);
        ibMarkets = findViewById(R.id.ibMarkets);
        ///so we get an intent from the ToActivity which gives us the selected City and we combine that information with Table name
        ///and the chosen category from this Activity and we pass it to the final activity

        ibFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(OfActivity.this, PriceActivity.class);
                String tab_name = getIntent().getStringExtra("Table_name");
                intent.putExtra("Table_name", tab_name);
                String name = getIntent().getStringExtra("Name");
                intent.putExtra("Name", name);
                intent.putExtra("Category", "Meal");
                startActivity(intent);

            }
        });
        ibMarkets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(OfActivity.this, PriceActivity.class);
                String tab_name = getIntent().getStringExtra("Table_name");
                intent.putExtra("Table_name", tab_name);
                String name = getIntent().getStringExtra("Name");
                intent.putExtra("Name", name);
                intent.putExtra("Category", "Market");
                startActivity(intent);

            }
        });
        ibTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(OfActivity.this, PriceActivity.class);
                String tab_name = getIntent().getStringExtra("Table_name");
                intent.putExtra("Table_name", tab_name);
                String name = getIntent().getStringExtra("Name");
                intent.putExtra("Name", name);
                intent.putExtra("Category", "Transport");
                startActivity(intent);

            }
        });
        ibGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(OfActivity.this, PriceActivity.class);
                String tab_name = getIntent().getStringExtra("Table_name");
                intent.putExtra("Table_name", tab_name);
                String name = getIntent().getStringExtra("Name");
                intent.putExtra("Name", name);
                intent.putExtra("Category", "Goods");
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
