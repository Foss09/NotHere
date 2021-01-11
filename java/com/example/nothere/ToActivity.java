package com.example.nothere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ToActivity extends AppCompatActivity implements CityAdapter.WhenClicked {
    //Here the user has to choose which city they want to discover
    //The Architecture should force the user to take one path throughout the whole app

    TextView tvPick;
    RecyclerView rvTo;
    ImageView ivTo;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    VieweModel vieweModel;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to);
        ivTo=findViewById(R.id.ivTo);
        tvPick=findViewById(R.id.tvPick);
        rvTo=findViewById(R.id.rvTo);
        intent=new Intent(ToActivity.this,OfActivity.class);
        layoutManager= new LinearLayoutManager(this);
        rvTo.setLayoutManager(layoutManager);
        vieweModel= new VieweModel(this);
        try {
           adapter=new CityAdapter(this,vieweModel.getCities());
           rvTo.setAdapter(adapter);


        } catch (ExecutionException e) {
           e.printStackTrace();
       } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void click(String s) {
        //when an item is clicked from the recyclerview we pass here the appropriate Table name to the next activity
        intent.putExtra("Table_name",s);

    }

    @Override
    public void clickName(String f) {
        //when an item is clicked from the recyclerview we pass here the appropriate City name to the next activity
        intent.putExtra("Name",f);
        startActivity(intent);
    }
}