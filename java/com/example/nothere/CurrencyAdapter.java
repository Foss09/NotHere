package com.example.nothere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

///////ADAPTER FOR THE RECYCLER VIEW OF CURRENCY CHOCIE////////

public class CurrencyAdapter extends RecyclerView.Adapter <CurrencyAdapter.Viewer>{
    ArrayList<String> theList;
    Context context;
    public CurrencyAdapter(Context con, ArrayList<String> list){
        theList=list;
        context=con;
    }
    public interface Chosen{
        void chose(String s);
    }
    public class Viewer extends RecyclerView.ViewHolder {
        TextView textView;
        public Viewer(@NonNull final View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tvCurrencyName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Chosen ch= (Chosen) context;
                    ch.chose((String) itemView.getTag());
                }
            });
        }
    }

    @NonNull
    @Override
    public Viewer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.currency_item,parent,false);
        return new Viewer(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewer holder, int position) {
        holder.itemView.setTag(theList.get(position));
        holder.textView.setText(theList.get(position));


    }

    @Override
    public int getItemCount() {
        return theList.size();
    }
}
