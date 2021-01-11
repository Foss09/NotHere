package com.example.nothere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//////////THIS ADAPTER IS FOR THE PRICES' RECYCLER VIEW///////////

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHoder> {
    ArrayList<String[]> theList;
    Context context;
    float rate;
    String currency;
    public PriceAdapter(ArrayList<String[]> list, Context con,String cur, float rat){
        theList=list;
        context=con;
        rate=rat;
        currency=cur;
    }
    public class ViewHoder extends RecyclerView.ViewHolder {
        ImageView ivPrice;
        TextView tvName,tvDetail,tvPrice;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            ivPrice=itemView.findViewById(R.id.ivPrice);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvName=itemView.findViewById(R.id.tvName);
            tvDetail=itemView.findViewById(R.id.tvDetail);

        }
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.price_item,parent,false);
        return new ViewHoder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        String full= theList.get(position)[0];
        String name=full.substring(0,full.indexOf(','));
        String detail=full.substring(full.indexOf(',')+1);
        String priceString = theList.get(position)[1];
        float price =( Float.parseFloat(priceString.substring(0,priceString.indexOf('.')+2)) )* rate;
        String price_toString=Float.toString(price);
        String price_final=price_toString.substring(0,price_toString.indexOf('.')+2);

        holder.tvName.setText(name);
        holder.tvDetail.setText(detail);
        holder.tvPrice.setText(price_final+" "+currency);
        switch (name){
            case "Meal": holder.ivPrice.setImageResource(R.drawable.meal);
            break;
            case "McMeal": holder.ivPrice.setImageResource(R.drawable.mcmeal);
                break;
            case "Beer": holder.ivPrice.setImageResource(R.drawable.beer_in);
                break;
            case "Imported beer": holder.ivPrice.setImageResource(R.drawable.beer_out);
                break;
            case "Cappuccino": holder.ivPrice.setImageResource(R.drawable.cappucino);
                break;
            case "Cocacola or Pepsi": holder.ivPrice.setImageResource(R.drawable.coke);
                break;
            case "Milk": holder.ivPrice.setImageResource(R.drawable.milk);
                break;
            case "Loaf of White Bread": holder.ivPrice.setImageResource(R.drawable.bread);
                break;
            case "Eggs": holder.ivPrice.setImageResource(R.drawable.eggs);
                break;
            case "Apples": holder.ivPrice.setImageResource(R.drawable.apple);
                break;
            case "Banana": holder.ivPrice.setImageResource(R.drawable.banana);
                break;
            case "Wine": holder.ivPrice.setImageResource(R.drawable.wine);
                break;
            case "Beef Round": holder.ivPrice.setImageResource(R.drawable.beef);
                break;
            case "One-way Ticket": holder.ivPrice.setImageResource(R.drawable.day_traffic);
                break;
            case "Monthly Pass": holder.ivPrice.setImageResource(R.drawable.month_traffic);
                break;
            case "Taxi": holder.ivPrice.setImageResource(R.drawable.taxi);
                break;
            case "Gasoline": holder.ivPrice.setImageResource(R.drawable.gas);
                break;
            case "Gym": holder.ivPrice.setImageResource(R.drawable.gym_quality);
                break;
            case "Jeans": holder.ivPrice.setImageResource(R.drawable.jeans);
                break;
            case "One Bedroom Apartment": holder.ivPrice.setImageResource(R.drawable.one_bed);
                break;
            case "Three Bedroom Apartment": holder.ivPrice.setImageResource(R.drawable.three_bed);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return theList.size();
    }
}
