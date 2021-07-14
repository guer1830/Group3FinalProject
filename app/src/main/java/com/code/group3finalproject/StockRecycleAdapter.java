package com.code.group3finalproject;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.code.group3finalproject.db.model.Stock;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;


public class StockRecycleAdapter extends RecyclerView.Adapter<StockRecycleAdapter.ViewHolder> {

    private List<Stock> stocks;

    public StockRecycleAdapter(List<Stock> stocks) {
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
        holder.stockSymbol.setText(stocks.get(position).getSymbol());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(view.getClass().getSimpleName(),
                        holder.stockSymbol.getText().toString());
                Intent intent = new Intent(view.getContext(),StockDetailActivity.class);
                intent.putExtra("StockSymbol", holder.stockSymbol.getText().toString());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public void refreshData(List<Stock> stocks) {
        this.stocks = stocks;
        notifyDataSetChanged();
    }

    public void deleteStock(int position) {
        this.stocks.remove(position);
        notifyDataSetChanged();
    }

    public void filter(String filter) {
        Log.i("Stock Adapter","items in list before filter: " + this.stocks.size());
        this.stocks = this.stocks.stream().filter(s -> s.getSymbol().startsWith(filter)).collect(Collectors.toList());
        Log.i("Stock Adapter","items in list after filter: " + this.stocks.size());
        notifyDataSetChanged();
    }

    public Stock getItem(int position) {
        return this.stocks.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stockSymbol;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            stockSymbol = itemView.findViewById(R.id.stockSymbol);
        }
    }
}