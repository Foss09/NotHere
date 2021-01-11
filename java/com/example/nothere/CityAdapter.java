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

////////THE ADAPTER FOR THE CITIES' RECYCLER VIEW/////////

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>  {
    Context context;
    ArrayList<String[]> theList;
    public CityAdapter(Context con, ArrayList<String[]> lis){
        context=con;
        theList=lis;
    }
    public interface WhenClicked {
        void click(String s);
        void clickName(String f);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCity;
        TextView tvCityName, tvCityCountry;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ivCity=itemView.findViewById(R.id.ivCity);
            tvCityName=itemView.findViewById(R.id.tvCityName);
            tvCityCountry=itemView.findViewById(R.id.tvCityCountry);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //when an item is clicked we pass here the name of the table in "click" and the name of the city in "clickName"
                    WhenClicked clicky= (WhenClicked) context;
                    String[] getExpected =(String[]) itemView.getTag();

                    clicky.click(getExpected[2]);
                    clicky.clickName(getExpected[0]);
                }
            });

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.city_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //the Tag gets  the Name of the city at index 0, the country at index 1 and the name of the appropriate table at 2
        holder.itemView.setTag(theList.get(position));
        holder.tvCityName.setText(theList.get(position)[0]);
        holder.tvCityCountry.setText(theList.get(position)[1]);
        switch (theList.get(position)[0]){
            case "Berlin":holder.ivCity.setImageResource(R.drawable.berlin);
            break;
            case "London":holder.ivCity.setImageResource(R.drawable.london_quality);
            break;
            case "New York":holder.ivCity.setImageResource(R.drawable.new_york_quality);
            break;
            case "Paris":holder.ivCity.setImageResource(R.drawable.paris_quality);
            break;
            case "Tokyo":holder.ivCity.setImageResource(R.drawable.tokyo);
            break;
            case "Hamburg":holder.ivCity.setImageResource(R.drawable.hamburg);
                break;
            case "Munich":holder.ivCity.setImageResource(R.drawable.munich);
                break;
            case "Moscow":holder.ivCity.setImageResource(R.drawable.moscow);
                break;
            case "Algiers":holder.ivCity.setImageResource(R.drawable.algiers);
                break;
            case "Los Angeles":holder.ivCity.setImageResource(R.drawable.la);
                break;
            case "Chicago":holder.ivCity.setImageResource(R.drawable.chicago);
                break;
            case "Boston":holder.ivCity.setImageResource(R.drawable.boston);
                break;
            case "San Francisco":holder.ivCity.setImageResource(R.drawable.sanfranc);
                break;
            case "Saint Petersburg":holder.ivCity.setImageResource(R.drawable.saintpeter);
                break;
            case "Frankfurt":holder.ivCity.setImageResource(R.drawable.frankfurt);
                break;
            case "Madrid":holder.ivCity.setImageResource(R.drawable.madrid);
                break;
            case "Barcelona":holder.ivCity.setImageResource(R.drawable.barcelona);
                break;
            case "Milan":holder.ivCity.setImageResource(R.drawable.milan);
                break;
            case "Amsterdam":holder.ivCity.setImageResource(R.drawable.amsterdam);
                break;
            case "Stockholm":holder.ivCity.setImageResource(R.drawable.stockholm);
                break;
            case "Copenhagen":holder.ivCity.setImageResource(R.drawable.copenhagen);
                break;
            case "Oslo":holder.ivCity.setImageResource(R.drawable.oslo);
                break;
            case "Seoul":holder.ivCity.setImageResource(R.drawable.seoul);
                break;
            case "Dubai":holder.ivCity.setImageResource(R.drawable.dubai);
                break;
            case "Delhi":holder.ivCity.setImageResource(R.drawable.delhi);
                break;
            case "Toronto":holder.ivCity.setImageResource(R.drawable.toronto);
                break;
            case "Casablanca":holder.ivCity.setImageResource(R.drawable.casa);
                break;
            case "Helsinki":holder.ivCity.setImageResource(R.drawable.helsinki);
                break;
            default:holder.ivCity.setImageResource(R.drawable.berlin);
            break;
        }


    }

    @Override
    public int getItemCount() {
        return theList.size();
    }
}
