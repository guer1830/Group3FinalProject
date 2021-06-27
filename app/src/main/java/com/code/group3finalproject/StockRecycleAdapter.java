package com.code.group3finalproject;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StockRecycleAdapter extends RecyclerView.Adapter<StockRecycleAdapter.ViewHolder> {

    private ArrayList<String> stocks;

    public StockRecycleAdapter(ArrayList<String> stocks) {
        this.stocks = stocks;
    }

    @NonNull
    @NotNull
    @Override
    public StockRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_list_item_layout, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull StockRecycleAdapter.ViewHolder holder, int position) {
        holder.stockSymbol.setText(stocks.get(position));
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stockSymbol;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            stockSymbol = itemView.findViewById(R.id.stockSymbol);
        }
    }
}